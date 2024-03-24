package com.green.yogizogi.controller;

import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    //가게추가 페이지 이동
    @GetMapping("/register")
    public String StoreUpdate() {
        return "store/storeregister";
    }
    //가게추가 과정
    @PostMapping("/register")
    public String StoreUpdatePloc(StoreDTO storeDTO) {
        System.out.println(storeDTO.toString());
        Long storeId = storeService.StoreRegister(storeDTO);
        System.out.println("가게 등록 성공 : "+storeId);
        return "main";
    }
    @GetMapping("/list")
    public String StoreLIst(Model model) {
        List<StoreDTO> storeDTOList = storeService.storeListAll();
        System.out.println(storeDTOList.get(0).toString());
        model.addAttribute("storeDTOList", storeDTOList);
        return "store/storelist";
    }
}