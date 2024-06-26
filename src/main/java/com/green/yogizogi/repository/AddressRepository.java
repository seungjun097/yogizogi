package com.green.yogizogi.repository;

import com.green.yogizogi.entity.Address;
import com.green.yogizogi.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select a from Address a where a.member = :member order by a.regDate desc")
    List<Address> findByMember(@Param("member") Member member);
}
