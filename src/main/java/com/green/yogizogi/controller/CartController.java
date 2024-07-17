package com.green.yogizogi.controller;

import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.dto.CartMenuDTO;
import com.green.yogizogi.dto.CartMenuOptionDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartMenuService cartMenuService;
    private final CartService cartService;
    private final MenuService menuService;
    private final StoreService storeService;

    @GetMapping("/cart")
    public String cartPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String email = principalDetails.getUsername();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        if(cartDTO != null) {
            if(!cartDTO.getCartMenuDTOList().isEmpty()) {
                StoreDTO storeDTO = storeService.findStore(cartDTO.getStoreId());
                model.addAttribute("cartDTO", cartDTO);
                model.addAttribute("storeDTO", storeDTO);
            }else {
                model.addAttribute("cartDTO", null);
                model.addAttribute("storeDTO", null);
            }
        }else {
            model.addAttribute("cartDTO", null);
            model.addAttribute("storeDTO", null);
        }
        return "cart/cart";
    }

    @PostMapping("/insert")
    public @ResponseBody ResponseEntity cartRegister(@RequestBody CartMenuDTO cartMenuDTO,
                                                     @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String email = principalDetails.getUsername();
        cartMenuService.CartMenuRegister(email, cartMenuDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public @ResponseBody ResponseEntity cartMenuDelete(@RequestBody Map<String, Object> data,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long cartMenuId = Long.parseLong(data.get("cartMenuId").toString());
        String email = principalDetails.getUsername();
        cartMenuService.cartMenuDelete(cartMenuId);
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        return new ResponseEntity<> (cartDTO, HttpStatus.OK);
    }

    @PostMapping("/countUpdate")
    public @ResponseBody ResponseEntity cartCountUpdate(@RequestBody CartMenuDTO cartMenuDTO) {
        Long cartMenuId = cartMenuDTO.getCartMenu_id();
        int count = cartMenuDTO.getCount();
        cartMenuService.cartMenuCountUpdate(cartMenuId, count);
        return new ResponseEntity<String>("카운트 변경 성공", HttpStatus.OK);
    }

    @PostMapping("/optionChecked")
    public @ResponseBody ResponseEntity optionChecked(@RequestBody CartMenuOptionDTO cartMenuOptionDTO) {
        Long menuOptionId = cartMenuOptionDTO.getMenuOptionId();
        boolean checked = cartMenuOptionDTO.isChecked();
        System.out.println("카트체크변경 : "+menuOptionId+"체크여부 : "+checked);
        cartMenuService.cartMenuOptionChecked(menuOptionId, checked);
        return new ResponseEntity<String>("체크 변경 성공", HttpStatus.OK);
    }

    @GetMapping("/deleteAll/{storeId}")
    public String cartDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PathVariable("storeId") Long storeId) {
        String email = principalDetails.getUsername();
        System.out.println("카트 삭제 컨트롤러 진입");
        cartService.cartDeleteByEmail(email);
        return "redirect:/store/detail2/"+storeId;
    }

    @PostMapping("/cartList")
    public @ResponseBody ResponseEntity cartListShow(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        String email = principalDetails.getUsername();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
}
