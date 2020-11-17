package me.gyuwoon.inflearnthejavatest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Target(ElementType.METHOD) // 메소드에 사용할 수 있다. 
@Retention(RetentionPolicy.RUNTIME)// 이 애노테이션의 전략은 사용한 코드가 이 애노테이션 정보를 런타임까지 지속해야 한다
@Test // 테스트 애노테이션으로 사용할 것이다.
@Tag("slow") //slow 태그를 붙여달라 (@Test와 @Tag가 메타 애노테이션)
public @interface SlowTest {


}
