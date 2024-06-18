package com.green.yogizogi.service;

import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.dto.CartMenuDTO;
import com.green.yogizogi.dto.CartMenuOptionDTO;
import com.green.yogizogi.entity.Cart;

import java.util.ArrayList;
import java.util.List;

public interface CartService {
    CartDTO cartFindByMemberEmail(String memberEmail);

    CartDTO cartFindById(Long cartId);

    void cartDeleteByEmail(String email);

    default CartDTO entityToDTO(Cart cart) {
        List<CartMenuDTO> cartMenuDTOList = new ArrayList<>();
        CartDTO cartDTO = CartDTO.builder()
                .storeId(cart.getStore().getId())
                .cartId(cart.getId())
                .memberId(cart.getMember().getId())
                .build();
        //카트dto를 만들어 저장하기 전 카트메뉴dto리스트를 만들어 줌
        if(cart.getCartMenuList() != null) {
            cart.getCartMenuList().stream().forEach(cartMenu -> {
                CartMenuDTO cartMenuDTO = CartMenuDTO.builder()
                        .menu_id(cartMenu.getMenu().getId())
                        .member_id(cart.getMember().getId())
                        .cartMenu_id(cartMenu.getId())
                        .menu_name(cartMenu.getMenu().getMenuName())
                        .menu_price(cartMenu.getMenu().getMenuPrice())
                        .count(cartMenu.getCount())
                        .path(cartMenu.getPath())
                        .uuid(cartMenu.getUuid())
                        .imgName(cartMenu.getImgName())
                        .build();
                List<CartMenuOptionDTO> cartMenuOptionDTOList = new ArrayList<>();
                if(cartMenu.getCartMenuOptionList() != null) {
                    cartMenu.getCartMenuOptionList().stream().forEach(option -> {
                        CartMenuOptionDTO cartMenuOptionDTO = CartMenuOptionDTO.builder()
                                .MenuOptionId(option.getId())
                                .cartMenu_id(option.getCartMenu().getId())
                                .menuOption_name(option.getMenuOption().getOpName())
                                .menuOption_price(option.getMenuOption().getOpPrice())
                                .checked(option.isChecked())
                                .build();
                        cartMenuOptionDTOList.add(cartMenuOptionDTO);
                    });
                }
                cartMenuDTO.setCartMenuOptionDTOList(cartMenuOptionDTOList);
                cartMenuDTOList.add(cartMenuDTO);
            });
        }
        cartDTO.setCartMenuDTOList(cartMenuDTOList);
        return cartDTO;
    }
}

