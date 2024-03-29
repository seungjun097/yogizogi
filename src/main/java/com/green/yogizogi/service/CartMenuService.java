package com.green.yogizogi.service;

import com.green.yogizogi.dto.CartMenuDTO;

public interface CartMenuService {
    Long CartMenuRegister(String email, CartMenuDTO cartMenuDTO);
}
