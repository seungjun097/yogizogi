package com.green.yogizogi.controller;

import com.green.yogizogi.dto.MenuDTO;
import com.green.yogizogi.dto.MenuOptionDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.service.MemberService;
import com.green.yogizogi.service.MenuOptionService;
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
@RequiredArgsConstructor //필수 매개 변수를 갖는 생성자 생성, 생성자에 final 키워드 필드 포함될시 객체 생성후 필드값이 변경되지 않도록 보장
public class ManagerController {

    private final MenuService menuService;
    private final StoreService storeService;
    private final MemberService memberService;
    private final MenuOptionService menuOptionService;
    @GetMapping("/myStoreList")
    @Transactional // 여러개의 데이터작업을 하나로 묶음으로 일관성 유지 성공시 커밋 하나라도 실패시 롤백
    public String myStoreList(@AuthenticationPrincipal User user, Model model) {
        String email;
        if(user==null) {
            return "main";
        }else {
            email = user.getUsername();
        }
        Member member = memberService.userFindEmail(email);
        List<StoreDTO> storeDTOList = storeService.storeFindMemberEmail(email);
        model.addAttribute("storeDTOList", storeDTOList);
        return "manager/mystorelist";
    }

    @PostMapping("/menu/")
    @Transactional
    public @ResponseBody ResponseEntity menuRegister(@RequestBody MenuDTO menuDTO) { //RequestBody는 http본문에있는 데이터를 메서드의 매개변수로 바인딩
        menuService.MenuSave(menuDTO);
        List<MenuDTO> menuDTOList = storeService.findStore(menuDTO.getStore_id()).getMenuDTOList();
        try {
            return new ResponseEntity<List<MenuDTO>>(menuDTOList,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<String>("메뉴 리스트를 불러오는데 실패.", HttpStatus.OK);
        }
    }

    @PostMapping("/option/")
    @Transactional
    public @ResponseBody ResponseEntity optionRegister(@RequestBody MenuOptionDTO optionDTO) {
        menuOptionService.addOption(optionDTO);
        try {
            return new ResponseEntity<String>("옵션저장 성공", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<String>("메뉴 리스트를 불러오는데 실패.", HttpStatus.OK);
        }

    }
}
