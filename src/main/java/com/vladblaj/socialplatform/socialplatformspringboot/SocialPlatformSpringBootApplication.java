package com.vladblaj.socialplatform.socialplatformspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;

@SpringBootApplication
public class SocialPlatformSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialPlatformSpringBootApplication.class, args);

    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

}

