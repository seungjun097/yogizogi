package com.green.yogizogi.service;

import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.entity.StoreImage;
import com.green.yogizogi.repository.MemberRepository;
import com.green.yogizogi.repository.StoreImageRepository;
import com.green.yogizogi.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreImageRepository storeImageRepository;
    private final MemberRepository memberRepository;
    //카테고리번호와 주소로 내주위 매장목록 조회
    @Override
    public List<StoreDTO> storeList(int category, int address) {
        System.out.println("서비스임플 category: " +category + ", address: " + address );
        return storeRepository.storeList(category,address);
    }

    @Override
    @Transactional
    public StoreDTO findStore(Long store_id) {
        Store store = storeRepository.findById(store_id).get();
        return entityToDto(store);
    }

    @Override
    public Long StoreRegister(StoreDTO storeDTO) {
        Store store = DtoToEntity(storeDTO);
        storeRepository.save(store);
        return store.getId();
    }

    @Override
    public List<StoreDTO> storeListAll() {
        List<Store> storeList = storeRepository.findAll();
        List<StoreDTO> storeDTOList = storeList.stream()
                .map(store-> entityToDto(store)).collect(Collectors.toList());
        return storeDTOList;
    }
}
