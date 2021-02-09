package com.study;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.UUID;

@Configuration
public class FeignConfiguration implements RequestInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public FeignConfiguration() {
    }

    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            boolean flag = true;
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames == null) {
                template.header("RequestID", new String[]{UUID.randomUUID().toString().replaceAll("-", "")});
            } else {
                while(headerNames.hasMoreElements()) {
                    String name = (String)headerNames.nextElement();
                    if (name.equalsIgnoreCase("RequestID")) {
                        flag = false;
                    }
                }

                if (flag) {
                    template.header("RequestID", new String[]{UUID.randomUUID().toString().replaceAll("-", "")});
                }
            }
        } else {
            template.header("RequestID", new String[]{UUID.randomUUID().toString().replaceAll("-", "")});
        }

        this.logger.info("feign interceptor header:{}", template);
    }
}

