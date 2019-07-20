package com.kobobook.www.kobobook.interceptor;

import com.kobobook.www.kobobook.exception.UnauthorizedException;
import com.kobobook.www.kobobook.service.JwtService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    private static final String HEADER_AUTH = "Authorization";

    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String token = request.getHeader(HEADER_AUTH);
        if(token != null && jwtService.isUsable(token)) {
            logger.info("jwt인터셉터 인증 성공");
            return true;
        } else {
            logger.info("jwt인터셉터 인증 실패");
            response.setStatus(401);
            return false;
        }
    }

}
