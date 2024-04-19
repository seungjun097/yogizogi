package com.green.yogizogi.dto;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartMenuOptionDTO {
    private Long MenuOptionId;
    private Long cartMenu_id;
    private String menuOption_name;
    private int menuOption_price;
    private boolean checked;
}
