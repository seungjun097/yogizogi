package com.green.yogizogi.controller;

import com.green.yogizogi.dto.AddressDTO;
import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.service.AddressService;
import com.green.yogizogi.service.CartService;
import com.green.yogizogi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final CartService cartService;
    private MemberService memberService;
    private AddressService addressService;

    @GetMapping("/orderProgress")
    public String getSignUp(@AuthenticationPrincipal User user, Model model) {
        String email = user.getUsername();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        model.addAttribute("cartDTO", cartDTO);
//        if(addressService.addressList(email).isEmpty()) {
//            model.addAttribute("addressDTOList", null);
//        }else{
//            List<AddressDTO> addressDTOList = addressService.addressList(email);
//            model.addAttribute("addressDTOList", addressDTOList);
//        }
        return "order/orderProgress";
    }
}
