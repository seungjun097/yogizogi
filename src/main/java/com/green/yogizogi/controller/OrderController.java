package com.green.yogizogi.controller;

import com.green.yogizogi.dto.*;
import com.green.yogizogi.service.AddressService;
import com.green.yogizogi.service.CartService;
import com.green.yogizogi.service.OrderService;
import com.green.yogizogi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final StoreService storeService;
    private final CartService cartService;
    private final AddressService addressService;
    private final OrderService orderService;

    @GetMapping("/orderList")
    public String orderPage(@AuthenticationPrincipal User user, Model model) {
        String email = user.getUsername();
        List<OrderListDTO> orderListDTOList = orderService.orderList(email);
        model.addAttribute("orderDTOList", orderListDTOList);
        return "order/orderList";
    }

    @PostMapping("/orderCreate")
    public @ResponseBody ResponseEntity orderCreate(@RequestBody OrderDTO orderDTO,
                                                    @AuthenticationPrincipal User user) {
        String email = user.getUsername();
        orderService.orderCreate(orderDTO, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/orderProgress/{storeId}")
    public String getSignUp(@PathVariable("storeId") Long storeId,
                            @AuthenticationPrincipal User user, Model model) {
        System.out.println("주소요청 성공");
        String email = user.getUsername();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        model.addAttribute("cartDTO", cartDTO);
        model.addAttribute("userEmail", email);
        StoreDTO storeDTO = storeService.findStore(storeId);
        model.addAttribute("storeId", storeDTO.getId());
        model.addAttribute("storeName", storeDTO.getStoreName());
        model.addAttribute("deliveryTip", storeDTO.getDeliveryTip());
        List<AddressDTO> addressDTOList = addressService.addressList(email);
        model.addAttribute("addressDTOList", addressDTOList);
        return "order/orderProgress";
    }

}
