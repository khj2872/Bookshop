package com.kobobook.www.kobobook.util.logback;

import org.apache.catalina.valves.AccessLogValve;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessLogConfig implements WebServerFactoryCustomizer {
    @Override
    public void customize(WebServerFactory factory) {
        final TomcatServletWebServerFactory containerFactory = (TomcatServletWebServerFactory) factory;

        final AccessLogValve accessLogValve = new AccessLogValve();
        accessLogValve.setPattern("%h %t \"%r\" %s %b %D \"%{Referer}i\" \"%{User-Agent}i\"");
        accessLogValve.setDirectory(".");
        accessLogValve.setSuffix(".log");
        accessLogValve.setCondition("ignoreLogging");
        containerFactory.addContextValves(accessLogValve);
    }
}
