package com.green.yogizogi.repository;

import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{
    @Query("select o from Order o where o.member = :member order by o.regDate desc")
    List<Order> findByMember(@Param("member") Member member);
}
