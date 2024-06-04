package com.green.yogizogi.repository.search;

import com.green.yogizogi.entity.QReview;
import com.green.yogizogi.entity.QStore;
import com.green.yogizogi.entity.Store;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;


public class SearchStoreRepositoryImpl extends QuerydslRepositorySupport implements SearchStoreRepository {
    public SearchStoreRepositoryImpl() {
        super(Store.class);
    }

    @Override
    public List<Tuple> searchStoreByName(String name) {
        return null;
    }

    @Override
    public List<Object[]> storeSortBy(String name) {
        QStore store = QStore.store;
        QReview review = QReview.review;

        //JPQL조회 생성,조건
        JPQLQuery<Store> jpqlQuery = from(store)
                .leftJoin(review).on(review.store.eq(store));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(store, review.grade.avg().coalesce(0.0), review.count())
                .groupBy(store);

        if(name != null) {
            switch (name) {
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
        List<Tuple> result = tuple.fetch();
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
