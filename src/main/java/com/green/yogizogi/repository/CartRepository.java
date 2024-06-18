package com.green.yogizogi.repository;

import com.green.yogizogi.entity.Cart;
import com.green.yogizogi.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMember(Member member);
}
