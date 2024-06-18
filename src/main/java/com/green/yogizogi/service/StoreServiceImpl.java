package com.green.yogizogi.service;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.common.PageResultDTO;
import com.green.yogizogi.constant.StoreCategory;
import com.green.yogizogi.dto.MainStoreDTO;
import com.green.yogizogi.dto.StoreDTO;

import com.green.yogizogi.entity.*;
import com.green.yogizogi.repository.LikesRepository;
import com.green.yogizogi.repository.MemberRepository;
import com.green.yogizogi.repository.StoreRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Name;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;

    @Override
    @Transactional
    public StoreDTO findStore(Long store_id) {
        Store store = storeRepository.findById(store_id).get();
        return entityToDto(store);
    }
    @Override
    public PageResultDTO<StoreDTO, Store> storeListAll(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색조건 처리
        Page<Store> result = storeRepository.findAll(booleanBuilder, pageable);
        Function<Store, StoreDTO> fn = (entity->entityToDto(entity));
        PageResultDTO<StoreDTO, Store> resultDTO = new PageResultDTO<>(result, fn);
        return resultDTO;
    }

    @Override
    public List<StoreDTO> getStoresByCategoryAndAddress(StoreCategory category, int address) {
        List<Store> storeList = storeRepository.findStoresByCategoryAndAddress(category,address);
        List<StoreDTO> storeDTOList = storeList.stream().map(store -> entityToDto(store)).collect(Collectors.toList());
        return storeDTOList;
    }
    @Override
    public Long StoreRegister(StoreDTO storeDTO) {
        Store store = DtoToEntity(storeDTO);
        Member member = memberRepository.findById(storeDTO.getMember_id()).get();
        store.setMember(member);
        storeRepository.save(store);
        return store.getId();
    }

    @Override
    public List<MainStoreDTO> StoreAndAvgListAll() {
        List<MainStoreDTO> storeDTOList = storeRepository.findStoreWithReviewGrade();
        return storeDTOList;
    }

    @Override
    @Transactional
    public List<StoreDTO> storeFindMemberEmail(String email) {
        List<Store> storeList = storeRepository.findByMember(memberRepository.findByEmail(email));
        List<StoreDTO> storeDTOList = storeList.stream().map(store -> entityToDto(store)).collect(Collectors.toList());
        return storeDTOList;
    }

    @Override
    @Transactional
    public String isLikes(Long storeId, String email) {
        Long memberId = memberRepository.findByEmail(email).getId();
        System.out.println(memberId);
        Likes likes = likesRepository.findByMemberIdAndStoreId(memberId, storeId);
        if(likes != null) {
            return "Y";
        }
        return "N";
    }

    @Override
    public List<MainStoreDTO> StoreSearch(StoreCategory category, int address1, String sort, String search) {
        List<Object[]> results = storeRepository.storeSearch(category, address1, sort, search);
        List<MainStoreDTO> mainStoreDTOList = new ArrayList<>();
        for(Object[] objects : results) {
            Store store = (Store) objects[0];
            Double avg = (Double) objects[1];
            avg = Math.round(avg*10.0)/10.0;
            Long reviewCount = (Long) objects[2];
            MainStoreDTO mainStoreDTO = MainStoreDTO.builder()
                    .id(store.getId())
                    .store_name(store.getStore_name())
                    .category(store.getCategory())
                    .store_address1(store.getStore_address1())
                    .store_address2(store.getStore_address2())
                    .store_address3(store.getStore_address3())
                    .opening_time(store.getOpening_time())
                    .closing_time(store.getClosing_time())
                    .min_delivery(store.getMin_delivery())
                    .delivery_time(store.getDelivery_time())
                    .delivery_tip(store.getDelivery_tip())
                    .storeDes(store.getStoreDes())
                    .imgName(store.getImgName())
                    .uuid(store.getUuid())
                    .path(store.getPath())
                    .avgGrade(avg)
                    .reviewCount(reviewCount)
                    .build();
            mainStoreDTOList.add(mainStoreDTO);
        }
        return mainStoreDTOList;
    }


    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String keyword = requestDTO.getKeyword();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QStore qStore = QStore.store;
        BooleanExpression expression = qStore.id.gt(0L);
        booleanBuilder.and(expression);

        if(keyword == null || keyword.trim().isEmpty()) {
            return booleanBuilder;
        }

        booleanBuilder.and(qStore.store_name.contains(keyword));

        return booleanBuilder;
    }

    @Transactional
    public void likes(Long storeId, String likes, Long userId) {

        Likes likes1 = likesRepository.findByMemberIdAndStoreId(userId, storeId);
        if(likes1 == null) {
            likes1 = Likes.builder()
                    .store(storeRepository.findById(storeId).get())
                    .member(memberRepository.findById(userId).get())
                    .build();
            likesRepository.save(likes1);
        } else {
            likesRepository.delete(likes1);
        }
    }
    @Override
    public List<MainStoreDTO> likeList(String email) {
        Member member = memberRepository.findByEmail(email);
        List<Object[]> results = storeRepository.likeStoreSearch(member);
        List<MainStoreDTO> mainStoreDTOList = new ArrayList<>();
        for(Object[] objects : results) {
            Store store = (Store) objects[0];
            Double avg = (Double) objects[1];
            avg = Math.round(avg*10.0)/10.0;
            Long reviewCount = (Long) objects[2];
            MainStoreDTO mainStoreDTO = MainStoreDTO.builder()
                    .id(store.getId())
                    .store_name(store.getStore_name())
                    .category(store.getCategory())
                    .store_address1(store.getStore_address1())
                    .store_address2(store.getStore_address2())
                    .store_address3(store.getStore_address3())
                    .opening_time(store.getOpening_time())
                    .closing_time(store.getClosing_time())
                    .min_delivery(store.getMin_delivery())
                    .delivery_time(store.getDelivery_time())
                    .delivery_tip(store.getDelivery_tip())
                    .storeDes(store.getStoreDes())
                    .imgName(store.getImgName())
                    .uuid(store.getUuid())
                    .path(store.getPath())
                    .avgGrade(avg)
                    .reviewCount(reviewCount)
                    .build();
            mainStoreDTOList.add(mainStoreDTO);
        }
        return mainStoreDTOList;
    }
}
