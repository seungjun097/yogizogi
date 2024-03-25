package com.green.yogizogi.entity;

import com.green.yogizogi.constant.MenuType;
import com.green.yogizogi.constant.SellStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name= "my_menu_seq", sequenceName = "menu_seq", allocationSize = 1)
public class Menu {

    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "my_menu_seq")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;
    private String menuName;
    private int menuPrice;
    private String menuDesc;
    private SellStatus sellStatus;

    //메뉴 이미지
    private String uuid;
    private String imgName;
    private String path;


}
