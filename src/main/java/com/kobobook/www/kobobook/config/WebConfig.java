package com.kobobook.www.kobobook.config;

import com.kobobook.www.kobobook.filter.AccessLoggingFilter;
import com.kobobook.www.kobobook.interceptor.JwtInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private static final String[] EXCLUDE_PATHS = {
            "/api/members/register",
            "/api/categories",
            "/api/items/**",
            "/api/login/**"
    };

    private JwtInterceptor jwtInterceptor;

    private AccessLoggingFilter accessLoggingFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }

    @Bean
    public FilterRegistrationBean<AccessLoggingFilter> filterRegistrationBean() {
        FilterRegistrationBean<AccessLoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(accessLoggingFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}