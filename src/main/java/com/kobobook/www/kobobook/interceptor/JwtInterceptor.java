package com.kobobook.www.kobobook.interceptor;

import com.kobobook.www.kobobook.service.JwtService;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private static final String HEADER_AUTH = "Authorization";

    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        final String token = request.getHeader(HEADER_AUTH);
        try {
            if(token != null) {
                jwtService.jwtValidate(token);
                log.info("인증 성공");
                return true;
            } else {
                log.debug("nullToken");
                response.sendError(HttpStatus.SC_UNAUTHORIZED, "nullToken");
                return false;
            }
        } catch (ExpiredJwtException eje) {
            eje.printStackTrace();
            log.debug("expiredToken");
            response.sendError(HttpStatus.SC_UNAUTHORIZED, "expiredToken");
            return false;
        } catch (ClaimJwtException cje) {
            cje.printStackTrace();
            log.debug("unAuthorizedToken");
            response.sendError(HttpStatus.SC_UNAUTHORIZED, "unAuthorizedToken");
            return false;
        }

    }

}
