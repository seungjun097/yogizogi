package com.green.yogizogi.service;

import com.green.yogizogi.dto.MenuOptionDTO;
import com.green.yogizogi.entity.MenuOption;

public interface MenuOptionService {
    Long addOption(MenuOptionDTO optionDTO);

    void deleteOption(Long menuOptionId);

    default MenuOption DtoToEntity(MenuOptionDTO optionDTO) {
        MenuOption menuOption = MenuOption.builder()
                .opName(optionDTO.getOpName())
                .opPrice(optionDTO.getOpPrice())
                .build();
        return menuOption;
    }
}
