package com.markany.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) // 이 annotation이 어디로 갈것인가!(method면 method로 가서 붙는다!)
@Retention(RetentionPolicy.RUNTIME) // 종속기간-anntation의 유지기간!
public @interface Auth {
	public String value() default "USER";
}
