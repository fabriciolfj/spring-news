package com.github.fabrluc.practicespring.filter;

import com.github.fabrluc.practicespring.service.LoggingService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("loggingFilter")
@RequiredArgsConstructor
public class LoggingFilter implements Filter {

    private final LoggingService log;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        log.log(httpServletRequest.getMethod(),httpServletRequest.getRequestURI());
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
