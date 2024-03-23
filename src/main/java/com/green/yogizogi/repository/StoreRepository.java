package com.green.yogizogi.repository;

import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store,Long> {

    @Query(value = "select * from store where category = #{category} and store_address1 like #{address}||'%'"
    , nativeQuery = true)
    List<StoreDTO> storeList(int category, int address);
}
