package org.socketio.demo.config;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.socketio.demo.config.SecurityHandler.CustomAuthenticationFailureHandler;
import org.socketio.demo.config.SecurityHandler.LoginFailedHandler;
import org.socketio.demo.config.SecurityHandler.LoginSuccessHandler;
import org.socketio.demo.config.excetions.SecurityDoFilterAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailedHandler loginFailedHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final SessionRegistry sessionRegistry;
    private final CustomAuthenticationFailureHandler  customAuthenticationFailureHandler;

    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/join", "/").permitAll()
                .anyRequest().permitAll()
        );

        http.formLogin((formLogin) ->
                formLogin.loginProcessingUrl("/login")
                        .successHandler(loginSuccessHandler).failureHandler(loginFailedHandler));
        http.logout((formLogout)->
                formLogout.logoutUrl("/logout").deleteCookies("JSESSIONID").
                        logoutSuccessHandler(logoutSuccessHandler));


        http.addFilterBefore(authenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)))
                , UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(sess->sess.maximumSessions(1).sessionRegistry(sessionRegistry));
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter(customAuthenticationFailureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }


}
