package com.green.yogizogi.service;

import com.green.yogizogi.constant.SellStatus;
import com.green.yogizogi.dto.MenuDTO;
import com.green.yogizogi.entity.Menu;

public interface MenuService {
    void MenuSave(MenuDTO menuDTO);

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
}
