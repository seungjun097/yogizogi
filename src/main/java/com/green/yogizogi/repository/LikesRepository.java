package com.green.yogizogi.repository;

import com.green.yogizogi.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Query("select l from Likes l where l.member.id = :memberId and l.store.id = :storeId")
    Likes findByMemberIdAndStoreId(@Param("memberId") Long memberId, @Param("storeId") Long storeId);
}
