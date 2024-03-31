package com.green.yogizogi.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartId;
    private Long memberId;
    private List<CartMenuDTO> cartMenuDTOList;
}
