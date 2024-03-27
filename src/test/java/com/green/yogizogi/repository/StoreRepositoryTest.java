package com.green.yogizogi.repository;

import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Store;
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
}
