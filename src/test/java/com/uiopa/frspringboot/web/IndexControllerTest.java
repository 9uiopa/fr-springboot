package com.uiopa.frspringboot.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void 메인페이지_로딩(){
        //when
        //getForObject : HTTP GET 요청을 보내고, 서버로부터 응답을 받아 특정 타입(여기서는 String)으로 변환하여 반환
        String body = this.restTemplate.getForObject("/", String.class);// : html 문서 전체의 텍스트

        //then
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }
}
