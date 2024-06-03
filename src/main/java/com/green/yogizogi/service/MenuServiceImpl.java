package com.green.yogizogi.service;

import com.green.yogizogi.dto.MenuDTO;
import com.green.yogizogi.entity.Menu;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.repository.MenuRepository;
import com.green.yogizogi.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService{
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Override
    public Long findStoreByMenuId(Long menu_id) {
        Menu menu = menuRepository.findById(menu_id).get();
        Store store = menu.getStore();
        return store.getId();
    }

    @Override
    public void MenuSave(MenuDTO menuDTO) {
        Store store = storeRepository.findById(menuDTO.getStore_id()).get();
        Menu menu = DtoToEntity(menuDTO);
        menu.setStore(store);
        menuRepository.save(menu);
    }

    @Override
    public void menuDelete(Long menuId) {
        Menu menu = menuRepository.findById(menuId).get();
        menuRepository.delete(menu);
    }

    @Override
    public MenuDTO menuSelect(Long menuId) {
        Menu menu = menuRepository.findById(menuId).get();
        return enTityToDto(menu);
    }

    @Override
    public void menuModify(MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(menuDTO.getId()).get();
        menu.menuUpdate(menuDTO);
        menuRepository.save(menu);
    }
}
