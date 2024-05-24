package com.green.yogizogi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "myOrderMenu_seq", sequenceName = "orderMenu_seq", allocationSize = 1)
public class OrderMenu {
    @Id
    @Column(name = "orderMenu_id")
    @GeneratedValue(generator = "myOrderMenu_seq", strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String menuName;
    private int menuPrice;
    private int count;
    private String selectOp;
}
