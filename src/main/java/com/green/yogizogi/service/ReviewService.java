package com.green.yogizogi.service;

import com.green.yogizogi.dto.ReviewDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Review;
import com.green.yogizogi.entity.Store;

import java.util.List;

public interface ReviewService {
    //특정 가게의 모든 리뷰를 조회
    List<ReviewDTO> getListOfStore(Long store_id);
    //가게리뷰 추가하기
    Long register(ReviewDTO reviewDTO);
    //가게리뷰 수정하기
    void modify(ReviewDTO reviewDTO);
    //가게리뷰 삭제하기
    void remove(Long reviewnum);

    //dto --> entity로 변환 메소드
    default Review dtoToEntity(ReviewDTO reviewDTO){
        Review review = Review.builder()
                .reviewnum(reviewDTO.getReviewnum())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .build();
        return review;
    }
    
    //entity --> dto로 변환 메소드
    default ReviewDTO entityToDto(Review review){
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewnum(review.getReviewnum())
                .storeId(review.getStore().getId())
                .member_id(review.getMember().getId())
                .nickname(review.getMember().getNickname())
                .email(review.getMember().getEmail())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();
        return reviewDTO;
    }
}
