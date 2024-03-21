package com.green.yogizogi.service;

import com.green.yogizogi.constant.Role;
import com.green.yogizogi.dto.SignupDTO;
import com.green.yogizogi.entity.Member;

public interface MemberService {
    Long signUp(SignupDTO signupDTO);

    String userEmailChk(String email);

    default Member signUpDTOtoEntity(SignupDTO dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .phone(dto.getPhone())
                .role(Role.USER)
                .build();
        return member;
    }
}