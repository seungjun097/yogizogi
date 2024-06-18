package com.green.yogizogi.repository;

import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Review;
import com.green.yogizogi.entity.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //엔티티의 특정한 속성을 같이 로딩하도록 표시하는 어노테이션
    //LAZY를 EAGER 로딩으로 지정
    //리뷰 엔티티 필드에 멤버 패치를 줬기 때문에 어노테이션을 붙여준다.
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByStore(Store store); //기본키이기 때문에 메소드 추가

    //리뷰삭제
    void deleteByMember(Member member);//리뷰만 삭제할 수 있도록 메소드 하나

    @Query("select ROUND(AVG(r.grade), 1) from Review r where r.store = :store")
    Double totalStarEvg(@Param("store") Store store);

    @Query("SELECT r FROM Review r WHERE r.member = :member")
    List<Review> findByMember(@Param("member") Member member);
}
