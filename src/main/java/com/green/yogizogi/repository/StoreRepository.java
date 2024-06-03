package com.green.yogizogi.repository;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.constant.StoreCategory;
import com.green.yogizogi.dto.MainStoreDTO;
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

    @Query("SELECT s FROM Store s WHERE s.member = :member order by s.id desc")
    List<Store> findByMember(@Param("member") Member member);

    //store객체와 해당 store의 review 평균값을 리런하는 쿼리문
    @Query("SELECT new com.green.yogizogi.dto.MainStoreDTO(" +
            " s.id, s.category, s.store_name, s.store_address1, s.store_address2, s.store_address3," +
            " s.opening_time, s.closing_time, s.min_delivery, s.delivery_time, s.delivery_tip," +
            " s.uuid, s.imgName, s.path, s.storeDes, ROUND(AVG(r.grade), 1)) " +
            "FROM Store s LEFT JOIN Review r ON s.id = r.store.id " +
            "GROUP BY s.id, s.category, s.store_name, s.store_address1, s.store_address2, s.store_address3," +
            " s.opening_time, s.closing_time, s.min_delivery, s.delivery_time, s.delivery_tip," +
            " s.uuid, s.imgName, s.path, s.storeDes")
    List<MainStoreDTO> findStoreWithReviewGrade();


    @Query("SELECT s FROM Store s WHERE s.category = :category AND s.store_address1 LIKE CONCAT(:address, '%')")
    List<Store> findStoresByCategoryAndAddress(@Param("category") StoreCategory category,@Param("address") int address);


}
