package com.green.yogizogi.service;

import com.green.yogizogi.constant.Role;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.repository.MemberRepository;
import jakarta.transaction.Transactional;
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
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        if (provider.equals("google")) {
            log.info("구글 로그인");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(provider.equals("naver")) {
            log.info("네이버 로그인");
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        } else if(provider.equals("kakao")) {
            log.info("카카오 로그인");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String nickname = oAuth2UserInfo.getName();

        Optional<Member> optionalMember = memberRepository.findOptionalByEmail(loginId);
        Member member;

        if(optionalMember.isEmpty()) {
            member = Member.builder()
                    .email(loginId)
                    .nickname(nickname)
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
