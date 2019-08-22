//package com.kobobook.www.kobobook.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Component
//@Slf4j
//public class AccessLoggingFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        final String url = ((HttpServletRequest) req).getRequestURI();
//
//        if(url.equals("/profile") || url.equals("/actuator/health") || url.equals("/favicon.ico")) {
//            req.setAttribute("ignoreLogging", true);
//        }
//
//        chain.doFilter(req, res);
//    }
//
//}
