package com.green.yogizogi.service;

import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.entity.Cart;
import com.green.yogizogi.entity.CartMenu;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.repository.CartRepository;
import com.green.yogizogi.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceimpl implements CartService{
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    @Override
    public CartDTO cartFindByMemberEmail(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail);
        Cart cart = cartRepository.findByMember(member);
        if(cart != null) {
            if(!cart.getCartMenuList().isEmpty()) {
                return entityToDTO(cart);
            }else {
                cartRepository.delete(cart);
                return null;
            }
        }else {
            return null;
        }


    }

    @Override
    @Transactional
    public CartDTO cartFindById(Long cartId) {
        Cart cart = cartRepository.findById(cartId).get();
        if(cart != null) {
            return entityToDTO(cart);
        }else {
            return null;
        }
    }

    @Override
    @Transactional
    public void cartDeleteByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMember(member);
        System.out.println("삭제할 카트의 아이디 : "+cart.getId());
        cartRepository.delete(cart);
    }
}
