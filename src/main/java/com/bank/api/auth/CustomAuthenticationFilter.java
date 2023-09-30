package com.bank.api.auth;


import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFilter   implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        String requestHeader = servletRequest.get("Authorization");
//        logger.info("Header : {}", requestHeader);
//        String username = null;
//        String token = null;
//
//        String username = this.jwtHelper.getUsernameFromToken(token);
//
        filterChain.doFilter(servletRequest, servletResponse);
    }


    // Implement your authentication logic here.
}
