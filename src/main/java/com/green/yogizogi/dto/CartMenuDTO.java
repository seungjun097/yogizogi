package com.green.yogizogi.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartMenuDTO {
    private Long cartMenu_id;
    private Long member_id;
    private Long menu_id;
    private String menu_name;
    private int menu_price;

    private int count;

    List<CartMenuOptionDTO> cartMenuOptionDTOList = new ArrayList<>();
}
