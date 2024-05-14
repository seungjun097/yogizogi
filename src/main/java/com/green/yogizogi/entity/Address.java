package com.green.yogizogi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"member"})
@SequenceGenerator(name = "myAddress_seq", sequenceName = "address_seq", allocationSize = 1)
public class Address extends BaseEntity{
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myAddress_seq")
    private Long id;
    private String name;
    private String phone;
    private String address1;
    private String address2;
    private String address3;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
