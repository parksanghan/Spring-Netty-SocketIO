    package org.socketio.demo.config;

    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.ServletRequest;
    import jakarta.servlet.ServletResponse;
    import jakarta.servlet.http.Cookie;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import jakarta.servlet.http.HttpSession;
    import lombok.RequiredArgsConstructor;
    import org.socketio.demo.config.SecurityHandler.CustomAuthenticationFailureHandler;
    import org.socketio.demo.config.excetions.CustomAuthenticationException;
    import org.socketio.demo.config.excetions.DuplicateLoginException;
    import org.socketio.demo.config.excetions.SecurityDoFilterAuthenticationEntryPoint;
    import org.socketio.demo.errors.exception.CustomException;
    import org.socketio.demo.errors.exception.ErrorCode;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.AuthenticationException;
    import org.springframework.security.core.context.SecurityContext;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.session.SessionRegistry;
    import org.springframework.security.core.session.SessionRegistryImpl;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.web.authentication.AuthenticationFailureHandler;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.stereotype.Component;

    import java.io.IOException;
    import java.lang.reflect.InvocationTargetException;
    import java.security.Principal;
    import java.util.List;

    /**
     @apiNote 로그인 시 예외 처리 필터.사용자가 이미 로그인되어 있는지 확인하여 중복 로그인을 방지합니다.
     {@code @override doFilter}
     */

    @RequiredArgsConstructor

    public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

        private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
        public void unSucessfulAuthentication
                (HttpServletRequest request, HttpServletResponse response , AuthenticationException failed)
                throws IOException, ServletException {
            System.out.println("Unsuccessful authentication 호출");

            SecurityContextHolder.clearContext();
            setAuthenticationFailureHandler(customAuthenticationFailureHandler);
            AuthenticationFailureHandler handler =getFailureHandler();
            handler.onAuthenticationFailure(request, response, failed);
//            point.commence(request,response,failed);

         }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            // 일단 이렇게 했을때

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String baseUrl = httpRequest.getRequestURL().toString();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


            System.out.println(baseUrl); // 요청된 url
//            Cookie[]  cookies = httpRequest.getCookies();
//            Cookie jsessionCookie=searchJsessionid(cookies);

            System.out.println(httpRequest.getRequestURI());
//            if (jsessionCookie!=null)System.out.println(jsessionCookie.getValue());
            // 사용자가 이미 로그인되어 있는지 확인
            // 로그인 된 같은 유저가 또 로그인 요청할 때 거부처리
            try{
             if (authentication!=null) {
                 System.out.println("이미 로그인 된 유저의 요청");
                 if (authentication.isAuthenticated() && httpRequest.getRequestURI().equals("/login")) {
                     System.out.println("LOGIN 요청");
                     System.out.println("이미 로그인됨");
                     Cookie[]  cookies = httpRequest.getCookies();
                     Cookie jsessionCookie=searchJsessionid(cookies);
                     if (jsessionCookie!=null)System.out.println(jsessionCookie.getValue());
                     throw DuplicateLoginException.EXCEPTION;

                 }
             }

            }
            catch(DuplicateLoginException e){
                System.out.println("DuplicateLoginException exception");
                CustomAuthenticationException exception  =  new CustomAuthenticationException(e);
                unSucessfulAuthentication(httpRequest,httpResponse,exception);
                return;
            }
            System.out.println("중복로그인아님");
//            super.doFilter(httpRequest, httpResponse, chain); 이거를 호출하게 되면 doFilter를 두번호출하는 것으로 
//            왜냐하면 doFilter를 재정의해서 하는데 부모 super로 그대로 호출하면 response를 2번 주기 때문임


            System.out.println("chain.doFoFilter 호출됨.");
            chain.doFilter(request, response);

        }

        public static  boolean isValidSession
                (SessionRegistry sessionRegistry, Cookie cookie)
        {

            return sessionRegistry.getAllPrincipals().stream()
                    .map(UserDetails.class::cast)
                    .anyMatch(client -> sessionRegistry.getAllSessions(client, false)
                            .stream()
                            .anyMatch(sessionInformation ->
                                    sessionInformation.getSessionId().equals(cookie.getValue())));
        }

        public static  boolean isValidSession
                (SessionRegistry sessionRegistry, String cookieSessionId)
        {

            return sessionRegistry.getAllPrincipals().stream()
                    .map(UserDetails.class::cast)
                    .anyMatch(client -> sessionRegistry.getAllSessions(client, false)
                            .stream()
                            .anyMatch(sessionInformation ->
                                    sessionInformation.getSessionId().equals(cookieSessionId)));
        }
        public static Cookie searchJsessionid(Cookie[] cookies){
            if (cookies!=null){
                for(Cookie eacookie:cookies){
                    if (eacookie.getName().equals("JSESSIONID")){
                        return eacookie;
                    }
                }
            }
            return null;
        }

        @Bean
        public SessionRegistry sessionRegistry() {
            return new SessionRegistryImpl();
        }


    }
