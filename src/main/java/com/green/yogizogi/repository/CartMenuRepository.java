package com.green.yogizogi.repository;

import com.green.yogizogi.entity.CartMenu;
import com.green.yogizogi.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {
    CartMenu findByMenu(Menu menu);
}
