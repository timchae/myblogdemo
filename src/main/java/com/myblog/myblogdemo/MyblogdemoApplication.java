package com.myblog.myblogdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 시간 자동 변경이 가능하도록 합니다.
public class MyblogdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyblogdemoApplication.class, args);
    }

}
