package com.green.yogizogi.repository.search;

import com.green.yogizogi.constant.StoreCategory;
import com.green.yogizogi.entity.Member;
import com.querydsl.core.Tuple;

import java.util.List;

public interface SearchStoreRepository {
    List<Object[]> storeSearch(StoreCategory category, int address1 , String sort, String Search);

    List<Object[]> likeStoreSearch(Member member);
}
