package com.green.yogizogi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuOptionDTO {
    private Long id;
    private Long menu_id;
    private String opName;
    private int opPrice;
}
