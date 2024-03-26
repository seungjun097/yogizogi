package com.green.yogizogi.service;

import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.entity.StoreImage;

import java.util.List;

public interface StoreService {
    //카테고리 코드와 우편번호로 내주위에 매장목록 조회
    List<StoreDTO> storeList(int category, int address);

    StoreDTO findStore(Long store_id);

    Long StoreRegister(StoreDTO storeDTO);

    List<StoreDTO> storeListAll();

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
                .imgName(storeDTO.getImgName())
                .uuid(storeDTO.getUuid())
                .path(storeDTO.getPath())
                .build();
        return store;
    }
    default StoreDTO entityToDto(Store store) {
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
                .imgName(store.getImgName())
                .uuid(store.getUuid())
                .path(store.getPath())
                .build();
        return dto;
    }
}
