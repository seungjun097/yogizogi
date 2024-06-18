package com.green.yogizogi.repository;

import com.green.yogizogi.constant.MenuType;
import com.green.yogizogi.constant.SellStatus;
import com.green.yogizogi.entity.Menu;
import com.green.yogizogi.entity.Store;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MenuRepositoryTests {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Test
    @Transactional
    public void MenuSaveAndFind() {
        Store store = storeRepository.findById(1L).get();
        Menu menu = Menu.builder()
                .menuType(MenuType.MAIN)
                .menuPrice(10000)
                .menuName("하와이안피자")
                .menuDesc("테스트중")
                .uuid("00")
                .path("00")
                .imgName("테스트")
                .sellStatus(SellStatus.SELL)
                .store(store)
                .build();
        menuRepository.save(menu);
        List<Menu> menuList = store.getMenuList();
        menuList.stream().forEach(i-> System.out.println(i));
    }
}
