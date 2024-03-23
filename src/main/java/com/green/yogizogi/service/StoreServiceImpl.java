package com.green.yogizogi.service;

import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    //카테고리번호와 주소로 내주위 매장목록 조회
    @Override
    public List<StoreDTO> storeList(int category, int address) {
        System.out.println("서비스임플 category: " +category + ", address: " + address );
        return storeRepository.storeList(category,address);
    }
}
