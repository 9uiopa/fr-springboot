package com.uiopa.frspringboot.config.auth.dto;

import com.uiopa.frspringboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable { // Serializable을 구현하는 것은 해당 클래스의 인스턴스가 직렬화될 수 있다는 것을 표시하는 역할. 즉, 전송용이라는 표시
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}