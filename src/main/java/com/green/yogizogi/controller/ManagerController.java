package com.green.yogizogi.controller;

import com.green.yogizogi.dto.MenuDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.service.MenuService;
import com.green.yogizogi.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/")
@RequiredArgsConstructor
public class ManagerController {

    private final MenuService menuService;
    private final StoreService storeService;
    @GetMapping("/menu/{storeId}")
    public String menuPage(@PathVariable(name = "storeId")Long storeId, Model model) {
        StoreDTO storeDTO = storeService.findStore(storeId);
        model.addAttribute("storeDTO", storeDTO);
        return "manager/menuregister";
    }
    @PostMapping("/menu/{storeId}")
    @Transactional
    public @ResponseBody ResponseEntity menuRegister(@PathVariable(name = "storeId")Long storeId,
                                                     @RequestBody MenuDTO menuDTO) {
        //요청이 온 메뉴를 저장
        menuService.MenuSave(menuDTO);
        //그 후 Store에 저장된 메뉴 리스트를 리턴해야함.
        StoreDTO storeDTO = storeService.findStore(storeId);
        return null;
    }
}
