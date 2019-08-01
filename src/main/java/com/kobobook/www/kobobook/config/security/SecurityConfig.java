//package com.kobobook.www.kobobook.config.security;
//
//import com.kobobook.www.kobobook.service.AuthProvider;
//import com.kobobook.www.kobobook.service.MemberService;
//import lombok.AllArgsConstructor;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//@AllArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private MemberService memberService;
//
//    private AuthSuccessHandler authSuccessHandler;
//
//    private AuthFailureHandler authFailureHandler;
//
//    private AuthProvider authProvider;
//
//    private PasswordEncoder passwordEncoder;
//
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/members**", "/api/categories**", "/api/items**").permitAll()
////                .antMatchers("/api/**").authenticated()
//                .and()
//                .formLogin()
//                .successHandler(authSuccessHandler)
//                .failureHandler(authFailureHandler)
////                .loginProcessingUrl("/api/members/logins")
////                .usernameParameter("userEmail")
////                .passwordParameter("password")
//                .and()
//                .logout()
////                .and()
////                .authenticationProvider(authProvider)
//        ;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider);
////        auth.userDetailsService(memberService)
////                .passwordEncoder(passwordEncoder);
//    }
////
////    @Bean
////    @Override
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        return super.authenticationManagerBean();
////    }
//
//
//}
