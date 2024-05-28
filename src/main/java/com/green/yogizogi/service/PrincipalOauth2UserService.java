package com.green.yogizogi.service;

import com.green.yogizogi.constant.Role;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    //private final BCryptPasswordEncoder encoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}",oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String loginId = provider + "_" + providerId;

        Optional<Member> optionalMember = memberRepository.findOptionalByEmail(loginId);
        Member member;

        if(optionalMember.isEmpty()) {
            member = Member.builder()
                    .email(loginId)
                    .nickname(oAuth2User.getAttribute("name"))
                    .provider(provider)
                    .providerId(providerId)
                    .role(Role.USER)
                    .build();
            memberRepository.save(member);
        } else {
            member = optionalMember.get();
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }

}
