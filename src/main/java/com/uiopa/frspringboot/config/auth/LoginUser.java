package com.uiopa.frspringboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)// 이 어노테이션이 생성될 수 있는 위치 : parameter
@Retention(RetentionPolicy.RUNTIME) //RetentionPolicy.RUNTIME은 런타임까지 어노테이션 정보를 유지하도록 지정하는 것
public @interface LoginUser{

}
