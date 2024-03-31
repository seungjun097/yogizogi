package com.green.yogizogi.service;

import com.green.yogizogi.dto.CartMenuDTO;
import com.green.yogizogi.dto.CartMenuOptionDTO;
import com.green.yogizogi.entity.*;
import com.green.yogizogi.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMenuServiceImpl implements CartMenuService{
    private final CartMenuRepository cartMenuRepository;
    private final CartMenuOptionRepository cartMenuOptionRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;

    //email과 cartmenu dto 받아서 카트 메뉴 등록하는 과정
    @Override
    @Transactional
    public Long CartMenuRegister(String email, CartMenuDTO cartMenuDTO) {
        Member member = memberRepository.findByEmail(email);
        Cart cart;
        //해당 멤버에게 카트가 없으면 카트를 만듬
        if(cartRepository.findByMember(member) == null) {
            cart = Cart.builder()
                    .member(member)
                    .build();
            cart = cartRepository.save(cart);
        }else{
            cart = cartRepository.findByMember(member);
        }
        //카트에 해당 메뉴가 없으면 메뉴를 추가하고 있으면 카운트를 늘림
        Menu menu = menuRepository.findById(cartMenuDTO.getMenu_id()).get();
        if(cartMenuRepository.findByMenu(menu) == null) {
            CartMenu cartMenu = CartMenu.builder()
                    .cart(cart)
                    .menu(menu)
                    .count(cartMenuDTO.getCount())
                    .build();
            cartMenuRepository.save(cartMenu);
            //카트 메뉴를 만들고 메뉴에 올션 체크 여부를 역시 저장
            List<CartMenuOptionDTO> cartMenuOptionDTOList = cartMenuDTO.getCartMenuOptionDTOList();
            cartMenuOptionDTOList.stream().forEach(optionDTO -> {
                CartMenuOption cartMenuOption = CartMenuOption.builder()
                        .cartMenu(cartMenu)
                        .menuOption(menuOptionRepository.findById(optionDTO.getMenuOptionId()).get())
                        .checked(optionDTO.isChecked())
                        .build();
                cartMenuOptionRepository.save(cartMenuOption);
            });
        }else {
           CartMenu cartMenu = cartMenuRepository.findByMenu(menu);
           cartMenu.addCount(cartMenuDTO.getCount());
           cartMenuRepository.save(cartMenu);
        }
        return cart.getId();
    }
}
