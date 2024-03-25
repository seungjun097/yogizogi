package com.green.yogizogi.controller;

import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.service.MemberService;
import com.green.yogizogi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;
    private final MemberService memberService;

    //가게추가 페이지 이동
    @GetMapping("/register")
    public String StoreUpdate(@AuthenticationPrincipal User user, Model model) {
        if(user==null) {
            return "main";
        }else {
            Member member = memberService.userFindEmail(user.getUsername());
            model.addAttribute("member_id", member.getId());
            return "store/storeregister";
        }
    }
    //가게추가 과정
    @PostMapping("/register")
    public String StoreUpdateProc(StoreDTO storeDTO) {
        System.out.println(storeDTO.toString());
        Long storeId = storeService.StoreRegister(storeDTO);
        System.out.println("가게 등록 성공 : "+storeId);
        return "main";
    }
    @GetMapping("/list")
    public String StoreList(Model model) {
        List<StoreDTO> storeDTOList = storeService.storeListAll();
        System.out.println(storeDTOList.get(0).toString());
        model.addAttribute("storeDTOList", storeDTOList);
        return "store/storelist";
    }
    @GetMapping("/detail/{storeId}")
    public String storeDetatilview(@PathVariable("storeId") Long storeId, Model model) {
        System.out.println("가게 아이디 조회 : "+storeId);
        StoreDTO storeDTO = storeService.findStore(storeId);
        model.addAttribute("storeDTO", storeDTO);
        return "store/storedetail";
    }
}