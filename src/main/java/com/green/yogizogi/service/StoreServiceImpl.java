package com.green.yogizogi.service;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.common.PageResultDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.repository.MemberRepository;
import com.green.yogizogi.repository.MenuRepository;
import com.green.yogizogi.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

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
    public PageResultDTO<List<StoreDTO>, Store> storeListAll(PageRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<StoreDTO> search() {
        List<Store> storeList = storeRepository.findAll();
        List<StoreDTO> storeDTOList = storeList.stream()
                .map(store-> entityToDto(store)).collect(Collectors.toList());
        return storeDTOList;
    }

    @Override
    public List<StoreDTO> storeFindMember(Member member) {
        return null;
    }
}
