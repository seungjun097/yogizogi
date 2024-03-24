package com.green.yogizogi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StoreRepositoryTest {
    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void selectall() {
        List<Object[]> result = storeRepository.StoreAndImgList();
        System.out.println(result);
    }

}
