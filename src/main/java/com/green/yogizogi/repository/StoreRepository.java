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

    @Query("select s, si from Store s left outer join StoreImage si on si.store = s")
    Page<List<Object[]>> StoreAndImgList(Pageable pageable);
}
