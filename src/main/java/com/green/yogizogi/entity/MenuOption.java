package com.green.yogizogi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name= "my_option_seq", sequenceName = "option_seq", allocationSize = 1)
public class MenuOption {
    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "my_option_seq")
    private Long id;
    private int opPrice;
    private String opName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
