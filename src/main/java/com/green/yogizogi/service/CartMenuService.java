package com.green.yogizogi.service;

import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.dto.CartMenuDTO;

public interface CartMenuService {
    void CartMenuRegister(String email, CartMenuDTO cartMenuDTO);

    void cartMenuDelete(Long cartMenuId);
}
