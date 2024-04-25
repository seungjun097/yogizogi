package com.green.yogizogi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cartmenu")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "cartMenu_seq", sequenceName = "cartmenu_seq", allocationSize = 1)
public class CartMenu {
    @Id
    @Column(name = "cartmenu_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cartMenu_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
    private int count;

    @OneToMany(mappedBy = "cartMenu", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartMenuOption> cartMenuOptionList = new ArrayList<>();

    public void addCount(int count) {
        this.count += count;
    }

    public void updateCount(int count) {
        this.count = count;
    }

}
