package com.green.yogizogi.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private Long storeId;
    private String likes;
}
