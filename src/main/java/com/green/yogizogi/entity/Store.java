package com.green.yogizogi.entity;

import com.green.yogizogi.constant.StoreCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name= "my_store_seq", sequenceName = "store_seq", allocationSize = 1)
public class Store {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "my_store_seq")
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreCategory category;

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
    private int opening_time;
    @Column
    private int closing_time;
    @Column
    private int min_delivery;
    @Column
    private int delivery_time;
    @Column
    private int delivery_tip;
    @Lob
    @Column
    private String storeDes;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Menu> menuList = new ArrayList<>();

    public void addMenu(Menu menu) {
        menuList.add(menu);
        menu.setStore(this);
    }
}
