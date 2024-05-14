package com.green.yogizogi.controller;

import com.green.yogizogi.dto.AddressDTO;
import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.dto.OrderProgressDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.service.AddressService;
import com.green.yogizogi.service.CartService;
import com.green.yogizogi.service.MemberService;
import com.green.yogizogi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final CartService cartService;
    private final AddressService addressService;
    private final StoreService storeService;

    @GetMapping("/orderProgress/{storeId}")
    public String getSignUp(@PathVariable("storeId") Long storeId,
                            @AuthenticationPrincipal User user, Model model) {
        System.out.println("주소요청 성공");
        String email = user.getUsername();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        model.addAttribute("cartDTO", cartDTO);
        model.addAttribute("userEmail", email);
        StoreDTO storeDTO = storeService.findStore(storeId);
        model.addAttribute("storeName", storeDTO.getStoreName());
        model.addAttribute("deliveryTip", storeDTO.getDeliveryTip());
        List<AddressDTO> addressDTOList = addressService.addressList(email);
        model.addAttribute("addressDTOList", addressDTOList);
        return "order/orderProgress";
    }
}
