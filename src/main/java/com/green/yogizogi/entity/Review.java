package com.green.yogizogi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"store","member"})
@SequenceGenerator(name = "myReviewSeq", sequenceName = "review_seq", allocationSize = 1)
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myReviewSeq")
    private Long reviewnum;
    private String text;
    private int grade; //평점
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void changeGrade(int grade){
        this.grade = grade;
    }
    public void changeText(String text){
        this.text = text;
    }
}
