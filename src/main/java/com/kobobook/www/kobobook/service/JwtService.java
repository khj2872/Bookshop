package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    private static final String SALT = "MySecretSignature";

    private static final Charset encodeType = StandardCharsets.UTF_8;

    public static final String jwtHeader = "Authorization";

    public String createMember(Integer userId, String role) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
//                .setHeaderParam("regDate", System.currentTimeMillis())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) //만료시간 = 현재시간 + 7일
                .setIssuer("www.kobobook.com")
                .claim("userId", userId)
                .claim("role", role)
                .claim("regDate", System.currentTimeMillis())
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
    }

    private byte[] generateKey() {
        return SALT.getBytes(encodeType);
    }

    public void jwtValidate(String jwt) throws JwtException {
        Jwts.parser()
                .setSigningKey(this.generateKey())
                .parseClaimsJws(jwt);
    }

    public Map<String, Object> getMap(String key) throws UnauthorizedException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader(jwtHeader);
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SALT.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
        return value;
    }

    public Object getString(String key) throws UnauthorizedException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader(jwtHeader);
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SALT.getBytes(encodeType))
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
        return claims.getBody().get(key);

    }
}