package com.green.yogizogi.entity;

import com.green.yogizogi.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "myOrder_seq", sequenceName = "order_seq", allocationSize = 1)
public class Order extends BaseEntity{
    @Id
    @Column(name = "order_id")
    @GeneratedValue(generator = "myOrder_seq", strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private String merchant_uid;

    private int totalPrice;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERWAITING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenuList = new ArrayList<>();

    public void orderStatusChange(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
