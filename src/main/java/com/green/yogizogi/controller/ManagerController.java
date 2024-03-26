package com.green.yogizogi.controller;

import com.green.yogizogi.dto.MenuDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.service.MemberService;
import com.green.yogizogi.service.MenuService;
import com.green.yogizogi.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager/")
@RequiredArgsConstructor
public class ManagerController {

    private final MenuService menuService;
    private final StoreService storeService;
    private final MemberService memberService;
    @GetMapping("/myStoreList")
    public String myStoreList(@AuthenticationPrincipal User user, Model model) {
        String email;
        if(user==null) {
            return "main";
        }else {
            email = user.getUsername();
        }
        List<StoreDTO> storeDTOList = storeService
                .storeFindMember(memberService.userFindEmail(email));
        model.addAttribute("storeDTOList", storeDTOList);
        return "manager/mystorelist";
    }

    @PostMapping("/menu/{storeId}")
    @Transactional
    public @ResponseBody ResponseEntity menuRegister(@PathVariable(name = "storeId")Long storeId,
                                                     @RequestBody MenuDTO menuDTO) {
        menuService.MenuSave(menuDTO);
        List<MenuDTO> menuDTOList = storeService.findStore(storeId).getMenuDTOList();
        try {
            return new ResponseEntity<List<MenuDTO>>(menuDTOList,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<String>("메뉴 리스트를 불러오는데 실패.", HttpStatus.OK);
        }
    }
}
