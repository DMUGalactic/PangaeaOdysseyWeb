package com.PangaeaOdyssey.PangaeaOdyssey.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        //모든 컨트롤러의 경로에 대해 3000번 요청이 오는 주소
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");
    }
}
