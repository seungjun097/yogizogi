package com.green.yogizogi.service;

import com.green.yogizogi.constant.SellStatus;
import com.green.yogizogi.dto.MenuDTO;
import com.green.yogizogi.dto.MenuOptionDTO;
import com.green.yogizogi.entity.Menu;
import com.green.yogizogi.entity.MenuOption;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MenuService {

    Long findStoreByMenuId(Long menu_id);
    void MenuSave(MenuDTO menuDTO);

    void menuDelete(Long menuId);

    MenuDTO menuSelect(Long menuId);

    void menuModify(MenuDTO menuDTO);

    default Menu DtoToEntity(MenuDTO menuDTO) {
        Menu menu = Menu.builder()
                .menuName(menuDTO.getMenuName())
                .menuPrice(menuDTO.getMenuPrice())
                .sellStatus(SellStatus.SELL)
                .menuType(menuDTO.getMenuType())
                .menuDesc(menuDTO.getMenuDesc())
                .imgName(menuDTO.getImgName())
                .path(menuDTO.getPath())
                .uuid(menuDTO.getUuid())
                .build();
        return menu;
    }
    @Transactional
    default MenuDTO enTityToDto(Menu menu) {
        MenuDTO dto = MenuDTO.builder()
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
            dto.addOptionDTO(menuOptionDTO);
        });
        return dto;
    }
}
