package com.green.yogizogi.repository;

import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Menu;
import com.green.yogizogi.entity.Store;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StoreRepositoryTest {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void storefindbyemail() {
        Member member = memberRepository.findByEmail("green@daum.net");
        System.out.println(member.getNickname());
        List<Store> storeList = storeRepository.findByMember(member);
        System.out.println(storeList.get(0).getStore_name());
    }

    @Test
    @Transactional
    public void storemenuList() {
        Store store = storeRepository.findById(1L).get();
        List<Menu> menuList = store.getMenuList();
        menuList.stream().forEach(i-> System.out.println(i.getMenuName()));
    }

    @Test
    @Transactional
    public void storeTest() {
        Member member = memberRepository.findByEmail("green@green.com");
        List<Store> storeList = storeRepository.findByMember(member);
        System.out.println(storeList.size());
        System.out.println(storeList.get(0).getMenuList());
    }
}
