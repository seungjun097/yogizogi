package com.green.yogizogi.service;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.common.PageResultDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.entity.StoreImage;
import com.green.yogizogi.repository.MemberRepository;
import com.green.yogizogi.repository.StoreImageRepository;
import com.green.yogizogi.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreImageRepository storeImageRepository;
    private final MemberRepository memberRepository;


    @Override
    public Page<Store> searchByCategory(String keyword, Pageable pageable) {
        return storeRepository.findByCategoryContainingKeyword(keyword, pageable);
    }

    @Override
    public Page<Store> searchByStoreName(String keyword, Pageable pageable) {
        return storeRepository.findByStoreNameContainingKeyword(keyword, pageable);
    }

    @Override
    public Page<Store> searchByDeliveryTip(String keyword, Pageable pageable) {
        return storeRepository.findByDeliveryTipContainingKeyword(keyword, pageable);
    }

    @Override
    public Page<Store> searchByDeliveryTime(String keyword, Pageable pageable) {
        return storeRepository.findByDeliveryTimeContainingKeyword(keyword, pageable);
    }

    @Override
    @Transactional
    public StoreDTO findStore(Long store_id) {
        Store store = storeRepository.findById(store_id).get();
        StoreImage storeImage = storeImageRepository.findByStore(store);
        return entityToDto(store, storeImage);
    }

    @Override
    @Transactional
    public Long StoreRegister(StoreDTO storeDTO) {
        Store store = DtoToEntity(storeDTO);
        Optional<Member> result = memberRepository.findById(storeDTO.getMember_id());
        if(result.isPresent()) {
            store.setMember(result.get());
        }
        storeRepository.save(store);

        StoreImage storeImage = StoreImage.builder()
                .imgName(storeDTO.getImgName())
                .uuid(storeDTO.getUuid())
                .path(storeDTO.getPath())
                .store(store)
                .build();
        storeImageRepository.save(storeImage);
        return store.getId();
    }

    @Override
    public PageResultDTO<List<StoreDTO>, Store> storeListAll(PageRequestDTO requestDTO) {
        return null;
    }


    @Override
    public List<StoreDTO> search() {

        return null;
    }
}
