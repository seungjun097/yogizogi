package com.green.yogizogi.service;

import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.entity.Cart;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.repository.CartRepository;
import com.green.yogizogi.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceimpl implements CartService{
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public CartDTO cartFindByMemberEmail(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail);
        Cart cart = cartRepository.findByMember(member);
        if(cart != null) {
            return entityToDTO(cart);
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
}