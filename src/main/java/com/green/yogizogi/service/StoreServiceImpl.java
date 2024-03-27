package com.green.yogizogi.service;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.common.PageResultDTO;
import com.green.yogizogi.dto.StoreDTO;

import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.QStore;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.repository.MemberRepository;
import com.green.yogizogi.repository.StoreRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
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
    public PageResultDTO<StoreDTO, Store> storeListAll(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색조건 처리
        Page<Store> result = storeRepository.findAll(booleanBuilder, pageable);
        Function<Store, StoreDTO> fn = (entity->entityToDto(entity));
        PageResultDTO<StoreDTO, Store> resultDTO = new PageResultDTO<>(result, fn);
        resultDTO.setType(requestDTO.getType());
        resultDTO.setKeyword(requestDTO.getKeyword());
        return resultDTO;
    }

    @Override
    public Long StoreRegister(StoreDTO storeDTO) {
        Store store = DtoToEntity(storeDTO);
        Member member = memberRepository.findById(storeDTO.getMember_id()).get();
        store.setMember(member);
        storeRepository.save(store);
        return store.getId();
    }

    //상점 리스트
    /*@Override
    public PageResultDTO<StoreDTO,Store> storeListAll(PageRequestDTO RequestDTO) {
        Pageable pageable = RequestDTO.getPageable(Sort.by("id").descending());
        Page<Store> result = storeRepository.findAll(pageable);
        Function<Store,StoreDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result,fn);
    }*/
    @Override
    public List<StoreDTO> search() {
        return null;
    }

    @Override
    @Transactional
    public List<StoreDTO> storeFindMemberEmail(String email) {
        List<Store> storeList = storeRepository.findByMember(memberRepository.findByEmail(email));
        List<StoreDTO> storeDTOList = storeList.stream().map(store -> entityToDto(store)).collect(Collectors.toList());
        return storeDTOList;
    }
    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QStore qStore = QStore.store;
        BooleanExpression expression = qStore.id.gt(0L);
        booleanBuilder.and(expression);
        if(type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        /*if(type.contains("cate")) {
            conditionBuilder.or(qStore.category.contains(keyword));
        }
        if(type.contains("name")) {
            conditionBuilder.or(qStore.store_name.contains(keyword));
        }
        if(type.contains("time")) {
            conditionBuilder.or(qStore.delivery_time.contains(keyword));
        }
        if(type.contains("tip")) {
            conditionBuilder.or(qStore.delivery_tip.contains(keyword));
        }*/
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
}
