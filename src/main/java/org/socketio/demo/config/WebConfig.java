package org.socketio.demo.config;

import lombok.RequiredArgsConstructor;
import org.socketio.demo.config.SecurityHandler.CustomAuthenticationFailureHandler;
import org.socketio.demo.config.excetions.SecurityDoFilterAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final  SecurityDoFilterAuthenticationEntryPoint securityDoFilterAuthenticationEntryPoint;
    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler(securityDoFilterAuthenticationEntryPoint);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
 
}
