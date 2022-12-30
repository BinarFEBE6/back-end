//package org.binaracademy.finalproject.security;
//
//import org.binaracademy.finalproject.security.oauth.CustomOAuth2User;
//import org.binaracademy.finalproject.security.oauth.CustomOAuth2UserService;
//import org.binaracademy.finalproject.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfiguration {
//
//    @Autowired
//    private UserService userService;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .antMatchers( "/google", "/oauth/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .oauth2Login()
//                .userInfoEndpoint().userService(oAuth2UserService)
//                .and()
//                .successHandler((request, response, authentication) -> {
//                    CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
//                    userService.processOAuthPostLogin(oauthUser.getEmail(), oauthUser.getName());
//                    response.sendRedirect("/api/test");
//                })
//                .and()package org.binaracademy.finalproject.security;
////
////import org.binaracademy.finalproject.security.oauth.CustomOAuth2User;
////import org.binaracademy.finalproject.security.oauth.CustomOAuth2UserService;
////import org.binaracademy.finalproject.services.UserService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.web.SecurityFilterChain;
////
////@Configuration
////public class SecurityConfiguration {
////
////    @Autowired
////    private UserService userService;
////
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////
////        http.authorizeRequests()
////                .antMatchers( "/google", "/oauth/**").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .formLogin().permitAll()
////                .and()
////                .oauth2Login()
////                .userInfoEndpoint().userService(oAuth2UserService)
////                .and()
////                .successHandler((request, response, authentication) -> {
////                    CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
////                    userService.processOAuthPostLogin(oauthUser.getEmail(), oauthUser.getName());
////                    response.sendRedirect("/api/test");
////                })
////                .and()
////                .logout().permitAll();
////
////
////        http.headers().frameOptions().sameOrigin();
////        return http.build();
////    }
////    @Autowired
////    private CustomOAuth2UserService oAuth2UserService;
////}
//                .logout().permitAll();
//
//
//        http.headers().frameOptions().sameOrigin();
//        return http.build();
//    }
//    @Autowired
//    private CustomOAuth2UserService oAuth2UserService;
//}