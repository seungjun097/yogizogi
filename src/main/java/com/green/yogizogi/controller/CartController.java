package com.green.yogizogi.controller;

import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.dto.CartMenuDTO;
import com.green.yogizogi.service.CartMenuService;
import com.green.yogizogi.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartMenuService cartMenuService;
    private final CartService cartService;

    @PostMapping("/insert")
    public @ResponseBody ResponseEntity cartRegister(@RequestBody CartMenuDTO cartMenuDTO,
                                                     @AuthenticationPrincipal User user) {
        String email = user.getUsername();
        cartMenuService.CartMenuRegister(email, cartMenuDTO);
        //여기까지 저장은 되는데
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        return new ResponseEntity<CartDTO> (cartDTO, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public @ResponseBody ResponseEntity cartMenuDelete(@RequestBody Map<String, Object> data,
                                         @AuthenticationPrincipal User user) {
        Long cartMenuId = Long.parseLong(data.get("cartMenuId").toString());
        System.out.println("삭제 요청시 카트 메뉴 아이디 : "+cartMenuId);
        cartMenuService.cartMenuDelete(cartMenuId);
        String email = user.getUsername();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        return new ResponseEntity<CartDTO> (cartDTO, HttpStatus.OK);
    }
}
