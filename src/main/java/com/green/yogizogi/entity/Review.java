package com.green.yogizogi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"store","member"})
@SequenceGenerator(name = "myReviewSeq", sequenceName = "review_seq", allocationSize = 1)
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myReviewSeq")
    private Long reviewnum;
    private String text;

    @Builder.Default
    private int grade = 0; //평점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void changeGrade(int grade){
        this.grade = grade;
    }
    public void changeText(String text){
        this.text = text;
    }
}
