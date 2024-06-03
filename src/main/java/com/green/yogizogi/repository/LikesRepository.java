package com.green.yogizogi.repository;

import com.green.yogizogi.entity.Likes;
import com.green.yogizogi.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Query("select l from Likes l where l.member.id = :memberId and l.store.id = :storeId")
    Likes findByMemberIdAndStoreId(@Param("memberId") Long memberId, @Param("storeId") Long storeId);

    @Query("SELECT l.store FROM Likes l WHERE l.member.id = :memberId")
    List<Store> findLikedStoresByMemberId(@Param("memberId") Long memberId);
}
