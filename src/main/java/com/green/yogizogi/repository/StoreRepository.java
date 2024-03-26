package com.green.yogizogi.repository;

import com.green.yogizogi.constant.StoreCategory;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store,Long> {

    // 카테고리로 검색
    @Query("SELECT s FROM Store s WHERE LOWER(s.category) LIKE CONCAT('%', LOWER(:keyword), '%')")
    Page<Store> findByCategoryContainingKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 가게 이름으로 검색
    @Query("SELECT s FROM Store s WHERE LOWER(s.store_name) LIKE CONCAT('%', LOWER(:keyword), '%')")
    Page<Store> findByStoreNameContainingKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 배달 팁으로 검색
    @Query("SELECT s FROM Store s WHERE LOWER(s.delivery_tip) LIKE CONCAT('%', LOWER(:keyword), '%')")
    Page<Store> findByDeliveryTipContainingKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 배달 시간으로 검색
    @Query("SELECT s FROM Store s WHERE LOWER(s.delivery_time) LIKE CONCAT('%', LOWER(:keyword), '%')")
    Page<Store> findByDeliveryTimeContainingKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select s, si from Store s left outer join StoreImage si on si.store = s")
    Page<List<Object[]>> StoreAndImgList(Pageable pageable);
}
