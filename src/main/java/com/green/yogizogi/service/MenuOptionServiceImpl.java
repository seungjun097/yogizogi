package com.green.yogizogi.service;

import com.green.yogizogi.dto.MenuOptionDTO;
import com.green.yogizogi.entity.Menu;
import com.green.yogizogi.entity.MenuOption;
import com.green.yogizogi.repository.MenuOptionRepository;
import com.green.yogizogi.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuOptionServiceImpl implements MenuOptionService{

    private final MenuOptionRepository menuOptionRepository;
    private final MenuRepository menuRepository;
    @Override
    public Long addOption(MenuOptionDTO optionDTO) {
        Menu menu = menuRepository.findById(optionDTO.getMenu_id()).get();
        MenuOption menuOption = DtoToEntity(optionDTO);
        menuOption.setMenu(menu);
        menuOptionRepository.save(menuOption);
        return menuOption.getId();
    }

    @Override
    public void deleteOption(Long menuOptionId) {
        MenuOption menuOption = menuOptionRepository.findById(menuOptionId).get();
        menuOptionRepository.delete(menuOption);
    }
}
