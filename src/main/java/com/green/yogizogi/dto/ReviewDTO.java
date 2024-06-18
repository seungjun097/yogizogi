package com.green.yogizogi.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    //리뷰번호
    private Long reviewnum;
    //가게아이디
    private Long storeId;
    //회원정보
    //회원아이디
    private Long memberId;
    private Long orderId;
    private String nickname;
    private String email;
    private int grade;
    private String text;
    private LocalDateTime regDate, modDate;
}
