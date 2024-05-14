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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
@Transactional
public class ManagerController {

    private final MenuService menuService;
    private final StoreService storeService;
    private final MemberService memberService;
    private final MenuOptionService menuOptionService;

    @GetMapping("/update")
    @Transactional
    public String myStoreList(@AuthenticationPrincipal User user, Model model) {
        String email;
        if(user.getUsername().isEmpty()) {
            return "main";
        }else {
            email = user.getUsername();
        }
        Member member = memberService.userFindEmail(email);
        List<StoreDTO> storeDTOList = storeService.storeFindMemberEmail(email);
        model.addAttribute("storeDTOList", storeDTOList);
        if(!storeDTOList.isEmpty()) {
            model.addAttribute("storeDto", storeDTOList.get(0));
        }
        return "manager/storeupdate";
    }

    @PostMapping("/menu")
    @Transactional
    public @ResponseBody ResponseEntity menuRegister(@RequestBody MenuDTO menuDTO) {
        menuService.MenuSave(menuDTO);
        List<MenuDTO> menuDTOList = storeService.findStore(menuDTO.getStore_id()).getMenuDTOList();
        try {
            return new ResponseEntity<List<MenuDTO>>(menuDTOList,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<String>("메뉴 리스트를 불러오는데 실패.", HttpStatus.OK);
        }
    }

    @GetMapping("/update/{storeId}")
    @Transactional
    public String storeUpdate(@PathVariable("storeId") Long storeId,
                              @AuthenticationPrincipal User user,
                              Model model) {
        List<StoreDTO> storeDTOList = storeService.storeFindMemberEmail(user.getUsername());
        StoreDTO storeDTO = storeDTOList.stream().filter(storeDto -> storeDto.getId() == storeId)
                .collect(Collectors.toList()).get(0);
        model.addAttribute("storeDTOList", storeDTOList);
        model.addAttribute("storeDto", storeDTO);
        return "manager/storeupdate";
    }

    @PostMapping("/option")
    public @ResponseBody ResponseEntity optionRegister(@RequestBody MenuOptionDTO optionDTO) {
        System.out.println("추가된 옵션 이름"+optionDTO.getOpName());
        Long optionId =  menuOptionService.addOption(optionDTO);
        return new ResponseEntity<String>("옵션저장 성공"+optionId, HttpStatus.OK);
    }

    @DeleteMapping("/optionDelete/{menuOptionId}")
    public @ResponseBody ResponseEntity optionDelete( @PathVariable("menuOptionId") Long id) {
        menuOptionService.deleteOption(id);
        return new ResponseEntity<String>("옵션삭제 성공", HttpStatus.OK);
    }

    @DeleteMapping("/menuDelete/{menuId}")
    public @ResponseBody ResponseEntity menuDelete(@PathVariable("menuId") Long id) {
        menuService.menuDelete(id);
        return new ResponseEntity<>("메뉴삭제 성공", HttpStatus.OK);
    }

    @PostMapping("/modify")
    public @ResponseBody ResponseEntity menuModify(@RequestBody MenuDTO menuDTO) {
        menuService.menuModify(menuDTO);
        return new ResponseEntity<>("메뉴수정 성공", HttpStatus.OK);
    }
}
