package com.green.yogizogi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderMenuDTO {
    private Long orderMenu_id;
    private String menuName;
    private int menuPrice;
    private String selectOp;
    private int count;
}
