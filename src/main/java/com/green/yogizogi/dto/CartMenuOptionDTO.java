package com.green.yogizogi.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartMenuOptionDTO {
    private Long MenuOptionId;
    private Long Menu_id;
    private boolean checked;
}
