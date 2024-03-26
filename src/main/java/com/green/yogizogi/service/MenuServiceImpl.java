package com.green.yogizogi.service;

import com.green.yogizogi.dto.MenuDTO;
import com.green.yogizogi.entity.Menu;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.repository.MenuRepository;
import com.green.yogizogi.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    @Override
    public void MenuSave(MenuDTO menuDTO) {
        Store store = storeRepository.findById(menuDTO.getStore_id()).get();
        Menu menu = DtoToEntity(menuDTO);
        menu.setStore(store);
        menuRepository.save(menu);
    }
}
