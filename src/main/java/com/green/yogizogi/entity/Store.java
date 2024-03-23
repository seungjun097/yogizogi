package com.green.yogizogi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store")
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name= "my_store_seq", sequenceName = "store_seq", allocationSize = 1)
public class Store {
    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "my_store_seq")
    private Long id;
    @Column(nullable = false)
    private int category;
    @Column(nullable = false)
    private String store_name;
    @Column(nullable = false)
    private String store_address1;
    @Column(nullable = false)
    private String store_address2;
    @Column
    private String store_address3;
    @Column(nullable = false)
    private String store_phone;
    @Column
    private String store_img;
    @Column
    private String store_thumb;
    @Column
    private int opening_time = 0;
    @Column
    private int closing_time = 0;
    @Column
    private int min_delivery = 0;
    @Column
    private int delivery_time = 0;
    @Column
    private int delivery_tip = 0;
    @Lob
    @Column
    private String store_des = "가게소개가 없습니다";

}
