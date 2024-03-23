package com.green.yogizogi.controller;

import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class StoreController {
    StoreService storeService;
    @GetMapping("/store/{category}/{address1}")
    public String store(@PathVariable int category, @PathVariable int address1, Model model){

        List<StoreDTO> storeList = storeService.storeList(category,address1 /100);
        model.addAttribute("storeList", storeList);

        return "/views/store/store";
    }

}
