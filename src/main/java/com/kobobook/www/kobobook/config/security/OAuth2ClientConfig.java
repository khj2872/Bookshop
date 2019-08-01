//package com.kobobook.www.kobobook.config;
//
//import com.kobobook.www.kobobook.service.MemberService;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.web.filter.CompositeFilter;
//
//import javax.servlet.Filter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@EnableOAuth2Client
//@AllArgsConstructor
//public class OAuth2ClientConfig extends WebSecurityConfigurerAdapter {
//
//    private OAuth2ClientContext oAuth2ClientContext;
//
//    private MemberService memberService;
//
//    @Bean
//    @ConfigurationProperties("naver")
//    public ClientResources naver() {
//        return new ClientResources();
//    }
//
//    @Bean
//    @ConfigurationProperties("facebook")
//    public ClientResources facebook() {
//        return new ClientResources();
//    }
//
//    @Bean
//    @ConfigurationProperties("google")
//    public ClientResources google() {
//        return new ClientResources();
//    }
//
//    @Bean
//    @ConfigurationProperties("kakao")
//    public ClientResources kakao() {
//        return new ClientResources();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .antMatcher("/**").authorizeRequests()
//            .antMatchers("/", "/login**", "/api/members/*")
//            .permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .exceptionHandling()
//            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
//            .and()
//            .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
//
//        http.logout()
//            .invalidateHttpSession(true)
//            .clearAuthentication(true)
//            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//            .logoutSuccessUrl("/")
//            .permitAll();
//    }
//
//    @Bean
//    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//        return registration;
//    }
//
//    private Filter ssoFilter() {
//        CompositeFilter filter = new CompositeFilter();
//        List<Filter> filters = new ArrayList<>();
//        filters.add(ssoFilter(google(), "/login/google")); //  이전에 등록했던 OAuth 리다이렉트 URL
//        filters.add(ssoFilter(facebook(), "/login/facebook"));
//        filters.add(ssoFilter(naver(), "/login/naver"));
//        filters.add(ssoFilter(kakao(), "/login/kakao"));
//        filter.setFilters(filters);
//        return filter;
//    }
//
//    private Filter ssoFilter(ClientResources client, String path) {
//        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
//        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(client.getClient(), oAuth2ClientContext);
//        filter.setRestTemplate(restTemplate);
//        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
//        tokenServices.setRestTemplate(restTemplate);
//        filter.setTokenServices(tokenServices);
//        return filter;
//    }
//
//    @Bean
//    public AuthenticationManager customAuthenticationManager() throws Exception {
//        return authenticationManager();
//    }
//}
