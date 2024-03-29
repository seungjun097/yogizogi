package com.green.yogizogi.service;

import com.green.yogizogi.dto.CartMenuDTO;
import com.green.yogizogi.entity.Cart;
import com.green.yogizogi.entity.CartMenu;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Menu;
import com.green.yogizogi.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartMenuServiceImpl implements CartMenuService{
    private final CartMenuRepository cartMenuRepository;
    private final CartMenuOptionRepository cartMenuOptionRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public Long CartMenuRegister(String email, CartMenuDTO cartMenuDTO) {
        Member member = memberRepository.findByEmail(email);
        Cart cart;
        if(cartRepository.findByMember(member) == null) {
            cart = Cart.builder()
                    .member(member)
                    .build();
        }else{
            cart = cartRepository.findByMember(member);
        }
        Menu menu = menuRepository.findById(cartMenuDTO.getMenu_id()).get();
        if(cartMenuRepository.findByMenu(menu) == null) {
            CartMenu cartMenu = CartMenu.builder()
                    .cart(cart)
                    .menu(menu)
                    .count(cartMenuDTO.getCount())
                    .build();
            cartMenuRepository.save(cartMenu);

        }else {
           CartMenu cartMenu = cartMenuRepository.findByMenu(menu);
           cartMenu.addCount(cartMenuDTO.getCount());
           cartMenuRepository.save(cartMenu);
        }

        return null;
    }
}
