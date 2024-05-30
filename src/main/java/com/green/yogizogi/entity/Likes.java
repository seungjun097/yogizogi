package com.green.yogizogi.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "myLike_seq", sequenceName = "like_seq", allocationSize = 1)
public class Likes extends BaseEntity {

    @Id
    @GeneratedValue(generator = "myLike_seq", strategy = GenerationType.AUTO)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public Likes(Member member, Store store) {
        this.member = member;
        this.store = store;
    }
}
