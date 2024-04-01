package com.green.yogizogi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cartmenuoption")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "cartMenuOption_seq", sequenceName = "cartMenuOption_seq", allocationSize = 1)
public class CartMenuOption {
    @Id
    @Column(name = "cartmenuoption_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cartMenuOption_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cartmenu_id")
    private CartMenu cartMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private MenuOption menuOption;

    private boolean checked;

    public void CheckOn() {
        this.checked = true;
    }

    public void CheckOff() {
        this.checked = false;
    }
}
