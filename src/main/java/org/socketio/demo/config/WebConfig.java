package org.socketio.demo.config;

import lombok.RequiredArgsConstructor;
import org.socketio.demo.config.SecurityHandler.CustomAuthenticationFailureHandler;
import org.socketio.demo.config.excetions.SecurityDoFilterAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**") // /js/로 시작하는 모든 요청 처리
                .addResourceLocations("classpath:/fronted/src/js/"); // 실제 파일 위치
    }
    @Bean(value = "usersInRoomMap")
    public Map<String, List<UUID>> usersInRoomMap(){
        return new ConcurrentHashMap<>();
    }
    @Bean(value = "roomsSidMap")
     public Map<UUID,String > roomsSidMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean(value = "namesSidMap")
    public Map<UUID, String> namesSidMap() {
        return new ConcurrentHashMap<>();
    }
    @Bean(value = "langSidMap")
    public Map<UUID, String> langSidMap() {
        return new ConcurrentHashMap<>();
    }
}
