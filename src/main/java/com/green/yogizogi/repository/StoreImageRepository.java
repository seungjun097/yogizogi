package com.green.yogizogi.repository;

import com.green.yogizogi.entity.Store;
import com.green.yogizogi.entity.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {
    StoreImage findByStore(Store Store);
}
