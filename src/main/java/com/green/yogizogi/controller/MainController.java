package com.green.yogizogi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String mainPage() {
        return "main";
    }
    @GetMapping("/header")
    public String main(){
        return "/layout/header";
    }
    @GetMapping("/nav")
    public String nav(){
        return "/layout/nav";
    }
    @GetMapping("/register")
    public String register(){
        return "/layout/register";
    }
    @GetMapping("/main2")
    public String main2view() {
        return "main2";
    }
}