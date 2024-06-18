package com.green.yogizogi.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String imp_uid;
    private String merchant_uid;
    private String amount;

    @Builder
    public PaymentDTO(String imp_uid) {
        this.imp_uid = imp_uid;
    }
}
