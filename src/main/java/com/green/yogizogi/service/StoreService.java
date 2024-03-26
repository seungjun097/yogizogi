package com.green.yogizogi.service;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.common.PageResultDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.entity.StoreImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {
    // 카테고리로 검색
    Page<Store> searchByCategory(String keyword, Pageable pageable);

    // 가게 이름으로 검색
    Page<Store> searchByStoreName(String keyword, Pageable pageable);

    // 배달 팁으로 검색
    Page<Store> searchByDeliveryTip(String keyword, Pageable pageable);

    // 배달 시간으로 검색
    Page<Store> searchByDeliveryTime(String keyword, Pageable pageable);

    StoreDTO findStore(Long store_id);

    Long StoreRegister(StoreDTO storeDTO);

    PageResultDTO<List<StoreDTO>,Store> storeListAll(PageRequestDTO requestDTO);

    List<StoreDTO> search();

    default Store DtoToEntity(StoreDTO storeDTO) {
        Store store = Store.builder()
                .category(storeDTO.getCategory())
                .store_name(storeDTO.getStoreName())
                .store_address1(storeDTO.getStoreAddress1())
                .store_address2(storeDTO.getStoreAddress2())
                .store_address3(storeDTO.getStoreAddress3())
                .store_phone(storeDTO.getStorePhone())
                .opening_time(storeDTO.getOpeningTime())
                .closing_time(storeDTO.getClosingTime())
                .min_delivery(storeDTO.getMinDelivery())
                .delivery_time(storeDTO.getDeliveryTime())
                .delivery_tip(storeDTO.getDeliveryTip())
                .storeDes(storeDTO.getStoreDes())
                .build();
        return store;
    }
    default StoreDTO entityToDto(Store store, StoreImage storeImage) {
        StoreDTO dto = StoreDTO.builder()
                .id(store.getId())
                .category(store.getCategory())
                .storeName(store.getStore_name())
                .storeDes(store.getStoreDes())
                .storeAddress1(store.getStore_address1())
                .storeAddress2(store.getStore_address2())
                .storeAddress3(store.getStore_address3())
                .storePhone(store.getStore_phone())
                .openingTime(store.getOpening_time())
                .closingTime(store.getClosing_time())
                .deliveryTime(store.getDelivery_time())
                .deliveryTip(store.getDelivery_tip())
                .minDelivery(store.getMin_delivery())
                .imgName(storeImage.getImgName())
                .path(storeImage.getPath())
                .uuid(storeImage.getUuid())
                .build();
        return dto;
    }
}
