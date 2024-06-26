package com.green.yogizogi.controller;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final StoreService storeService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("storeDTOList", storeService.StoreAndAvgListAll());
        return "store/mainfirst";
    }
}