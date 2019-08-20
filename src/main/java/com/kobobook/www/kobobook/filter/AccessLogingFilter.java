package com.kobobook.www.kobobook.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AccessLogingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final String url = ((HttpServletRequest) req).getRequestURI();

        if(url.matches("/(profile|actuator)")) {
            req.setAttribute("ignoreLogging", true);
        }

        chain.doFilter(req, res);
    }

}
