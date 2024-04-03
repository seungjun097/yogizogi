package com.green.yogizogi.repository;

import com.green.yogizogi.constant.StoreCategory;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store,Long>, QuerydslPredicateExecutor<Store> {

    List<Store> findByMember(Member member);



    @Query("SELECT s FROM Store s WHERE s.category = :category AND s.store_address1 LIKE CONCAT(:address, '%')")
    List<Store> findStoresByCategoryAndAddress(@Param("category") StoreCategory category,@Param("address") int address);

}
