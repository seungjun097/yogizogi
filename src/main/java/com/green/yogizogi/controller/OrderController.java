package com.green.yogizogi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order/")
@RequiredArgsConstructor
public class OrderController {
    @GetMapping("/cart")
    public String getSignUp() {
        return "/store/detailtest";
    }

}
