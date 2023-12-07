package com.springsecurity.jwt2.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

import static com.springsecurity.jwt2.Utils.getClientIpAddr;

@Component
@Slf4j
public class IpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String requestURI = ((HttpServletRequest) request).getRequestURI();

        if(!requestURI.startsWith("/api/admin")){
            chain.doFilter(request, response);
            return;
        }

        String[] allowedIps = {"195.158.4.67", "127.0.0.1", "0:0:0:0:0:0:0:1"}; // load allowed ip list from database

        String requestIp = getClientIpAddr((HttpServletRequest) request);

        boolean allowed = Arrays.stream(allowedIps).anyMatch(requestIp::matches);

        if (allowed) {
            chain.doFilter(request, response);
        } else {
            throw new BadRequestException();
        }
    }
}
