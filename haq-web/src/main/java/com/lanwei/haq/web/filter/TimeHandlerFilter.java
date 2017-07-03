package com.lanwei.haq.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TimeHandlerFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(TimeHandlerFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        chain.doFilter(request, response);
        log.info("请求{}耗时：{}ms", path + request.getRequestURI(), System.currentTimeMillis() - start);
    }

    @Override
    public void destroy() {

    }
}
