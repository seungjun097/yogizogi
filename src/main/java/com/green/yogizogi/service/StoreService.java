package com.green.yogizogi.service;

import com.green.yogizogi.dto.StoreDTO;

import java.util.List;

public interface StoreService {
    //카테고리 코드와 우편번호로 내주위에 매장목록 조회
    List<StoreDTO> storeList(int category, int address);

}
