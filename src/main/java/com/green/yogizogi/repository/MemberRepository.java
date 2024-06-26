package com.green.yogizogi.repository;

import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select count(i) from Member i where i.email = :email")
    Long findByUserCount(@Param("email") String email);

    Member findByEmail(String email);

    Optional<Member> findOptionalByEmail(String email);

}