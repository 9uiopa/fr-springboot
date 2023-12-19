package com.uiopa.frspringboot.config.auth;

import com.uiopa.frspringboot.config.auth.dto.OAuthAttributes;
import com.uiopa.frspringboot.config.auth.dto.SessionUser;
import com.uiopa.frspringboot.domain.user.User;
import com.uiopa.frspringboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {// 로그인 이후 사용자의 정보를 기반으로 가입 및 세션 저장 등을 지원
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인 진행중인 서비스 구분용 코드
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails() //OAuth2 로그인 진행 시 키가 되는 필드값. PK
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        //로그인 성공 시 세션에 SessionUser 저장
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user)); // SessionUser: 세션에 사용자 정보를 저장하기 위한 DTO

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity()); // Optional 객체에 값이 있으면 반환하고 없으면(해당 이메일의 사용자가 없는 경우) attributes.toEntity() 실행
        return userRepository.save(user);
    }
}
