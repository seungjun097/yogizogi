package com.green.yogizogi.service;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.common.PageResultDTO;
import com.green.yogizogi.constant.StoreCategory;
import com.green.yogizogi.dto.MainStoreDTO;
import com.green.yogizogi.dto.MenuDTO;
import com.green.yogizogi.dto.MenuOptionDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.MenuOption;
import com.green.yogizogi.entity.Store;
import jakarta.transaction.Transactional;

import java.util.List;

public interface StoreService {
    void likes(Long storeId, String likes, Long userId);

    StoreDTO findStore(Long store_id);

    Long StoreRegister(StoreDTO storeDTO);

    PageResultDTO<StoreDTO,Store> storeListAll(PageRequestDTO requestDTO);

    //주소 카테고리값으로 주변가게 찾기
    public List<StoreDTO> getStoresByCategoryAndAddress(StoreCategory category, int address);

    List<MainStoreDTO> StoreAndAvgListAll();

    List<StoreDTO> storeFindMemberEmail(String email);

    String isLikes(Long storeId, String email);

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

    //store엔티티를 storeDTO바꾸며 store의 menu리스트도 menudto리스트로 바꿔 넣는다.
    default StoreDTO entityToDto(Store store) {
        StoreDTO storeDTO = StoreDTO.builder()
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
        store.getMenuList().stream().map(menu->{
            MenuDTO menuDTO = MenuDTO.builder()
                    .id(menu.getId())
                    .menuName(menu.getMenuName())
                    .menuPrice(menu.getMenuPrice())
                    .sellStatus(menu.getSellStatus())
                    .menuType(menu.getMenuType())
                    .menuDesc(menu.getMenuDesc())
                    .imgName(menu.getImgName())
                    .path(menu.getPath())
                    .uuid(menu.getUuid())
                    .store_id(menu.getStore().getId())
                    .build();
            List<MenuOption> optionList = menu.getOptionList();
            optionList.stream().forEach(option -> {
                MenuOptionDTO menuOptionDTO = MenuOptionDTO.builder()
                        .opPrice(option.getOpPrice())
                        .opName(option.getOpName())
                        .menu_id(menu.getId())
                        .id(option.getId())
                        .build();
                menuDTO.addOptionDTO(menuOptionDTO);
            });
            return menuDTO;
        }).forEach(menuDTO-> storeDTO.addMenuDTO(menuDTO));
        return storeDTO;
    }
    List<StoreDTO> likeList(String email);

}
