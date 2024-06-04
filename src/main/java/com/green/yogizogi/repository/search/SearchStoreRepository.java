package com.green.yogizogi.repository.search;

import com.querydsl.core.Tuple;

import java.util.List;

public interface SearchStoreRepository {
    List<Tuple> searchStoreByName(String name);

    List<Object[]> storeSortBy(String name);
}
