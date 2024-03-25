package com.green.yogizogi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// 해당 token을 바탕으로 개인 정보를 가져오기 위한 Response
@Data
@NoArgsConstructor
public class GoogleInfResponseDTO {
    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String email;
    private String email_verified;
    private String at_hash;
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
    private String locale;
    private String iat;
    private String exp;
    private String alg;
    private String kid;
    private String typ;
}