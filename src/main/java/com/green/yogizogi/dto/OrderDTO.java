package com.green.yogizogi.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long store_id;
    private String merchant_uid;
    private List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();
    private Long address_id;
    private int totalPrice;
}
