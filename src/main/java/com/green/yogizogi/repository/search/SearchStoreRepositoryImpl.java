package com.green.yogizogi.repository.search;

import com.green.yogizogi.constant.StoreCategory;
import com.green.yogizogi.entity.QReview;
import com.green.yogizogi.entity.QStore;
import com.green.yogizogi.entity.Store;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;


public class SearchStoreRepositoryImpl extends QuerydslRepositorySupport implements SearchStoreRepository {
    public SearchStoreRepositoryImpl() {
        super(Store.class);
    }

    @Override
    public List<Object[]> storeSearch(StoreCategory category, int address1 ,String sort, String search) {
        QStore store = QStore.store;
        QReview review = QReview.review;

        //JPQL조회 생성,조건
        JPQLQuery<Store> jpqlQuery = from(store)
                .leftJoin(review).on(review.store.eq(store));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(store, review.grade.avg().coalesce(0.0), review.count())
                .groupBy(store);

        System.out.println("category : " + category + " address : " + address1 +" Sort : " + sort + " search : "+ search);

        BooleanBuilder builder = new BooleanBuilder();
        //주소 조건
        if(address1 >= 10000 && address1 <= 99999) {
            int address = address1/100;
            String addressStr = String.valueOf(address);
            BooleanExpression expression = store.store_address1.substring(0, 3).eq(addressStr);
            builder.and(expression);
        }
        //카테고리 조건
        if(!category.equals(StoreCategory.DEFAULT)) {
            BooleanExpression expression = store.category.eq(category);
            builder.and(expression);
        }

        //검색 조건
        if(!search.equals("none")) {
            BooleanExpression expression = store.store_name.contains(search);
            builder.and(expression);
        }

        tuple.where(builder);

        //정렬 조건
        if(!sort.equals("none")) {
            switch (sort) {
                case "min_delivery":
                    tuple.orderBy(store.min_delivery.asc());
                    break;
                case "delivery_tip":
                    tuple.orderBy(store.delivery_tip.asc());
                    break;
                case "review":
                    tuple.orderBy(review.count().desc());
                    break;
                case "starAvg":
                    tuple.orderBy(review.grade.avg().desc());
                    break;
            }
        }
        //결과 적용
        List<Tuple> result = tuple.fetch();

        //결과를 오브젝트 배열로 전환
        List<Object[]> results = new ArrayList<>();
        for(Tuple obj : result) {
            Object[] objects = new Object[3];
            objects[0] = obj.get(QStore.store);
            objects[1] = obj.get(review.grade.avg().coalesce(0.0));
            objects[2] = obj.get(review.count());
            results.add(objects);
        }
        return results;
    }
}
