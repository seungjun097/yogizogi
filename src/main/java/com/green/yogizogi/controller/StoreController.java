package com.green.yogizogi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    //가게추가 페이지 이동
    @GetMapping("/update")
    public String StoreUpdate() {
        return "store/update";
    }
    //가게추가 과정
    @PostMapping("/update")
    public String StoreUpdatePloc() {
        return "/";
    }
}