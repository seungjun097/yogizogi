package com.green.yogizogi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProgressDTO {
    private Long storeId;
    private String storeName;
    private int deliveryTip;
    private int minDelivery;
}