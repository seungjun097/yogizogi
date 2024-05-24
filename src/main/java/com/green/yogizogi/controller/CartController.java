package com.green.yogizogi.controller;

import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.dto.CartMenuDTO;
import com.green.yogizogi.dto.CartMenuOptionDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.service.CartMenuService;
import com.green.yogizogi.service.CartService;
import com.green.yogizogi.service.MenuService;
import com.green.yogizogi.service.StoreService;
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
    public String cartPage(@AuthenticationPrincipal User user ,Model model) {
        String email = user.getUsername();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        if(cartDTO != null) {
            Long menu_id = cartDTO.getCartMenuDTOList().get(0).getMenu_id();
            Long store_id = menuService.findStoreByMenuId(menu_id);
            StoreDTO storeDTO = storeService.findStore(store_id);
            model.addAttribute("storeDTO", storeDTO);
        }
        model.addAttribute("cartDTO", cartDTO);
        return "cart/cart";
    }

    @PostMapping("/insert")
    public @ResponseBody ResponseEntity cartRegister(@RequestBody CartMenuDTO cartMenuDTO,
                                                     @AuthenticationPrincipal User user) {
        String email = user.getUsername();
        cartMenuService.CartMenuRegister(email, cartMenuDTO);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @PostMapping("/cartList")
    public @ResponseBody ResponseEntity cartListShow(@AuthenticationPrincipal User user) {
        String email = user.getUsername();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
}
