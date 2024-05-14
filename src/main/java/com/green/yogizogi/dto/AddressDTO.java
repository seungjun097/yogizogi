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
    private String name;
    private String phone;
    private String address1;
    private String address2;
    private String address3;
}
