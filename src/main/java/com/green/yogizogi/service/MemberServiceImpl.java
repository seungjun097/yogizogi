package com.green.yogizogi.service;

import com.green.yogizogi.dto.SignupDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long signUp(SignupDTO signupDTO) {
        Member member = signUpDTOtoEntity(signupDTO);
        member.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        member = memberRepository.save(member);
        return member.getId();
    }

    @Override
    public String userEmailChk(String email) {
        int result = memberRepository.findByUserCount(email).intValue();
        if(result != 0) {
            return "used";
        }
        return "ok";
    }

    @Override
    public Member userFindEmail(String email) {
        return memberRepository.findByEmail(email);
    }


    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username);
        if(member == null) {
            throw new UsernameNotFoundException(username);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username);
        if(member == null) {
            throw new UsernameNotFoundException(username);
        }
        return new PrincipalDetails(member);
    }
}