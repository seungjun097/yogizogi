package com.green.yogizogi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long address_id;
    private String storeAddress1;
    private String storeAddress2;
    private String storeAddress3;
}
