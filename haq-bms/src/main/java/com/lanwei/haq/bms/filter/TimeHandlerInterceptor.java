package com.lanwei.haq.bms.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截每个controller的执行时间
 *
 * @author liuxinyun
 * @date 2016/12/30 11:03
 */
public class TimeHandlerInterceptor implements HandlerInterceptor {

    private final static Logger log = LoggerFactory.getLogger(TimeHandlerInterceptor.class);

    /**
     * 执行controller之前放入开始时间
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    /**
     * 获取<code>preHandle</code>方法放入的开始时间，计算执行多少时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            long startTime = (Long) request.getAttribute("startTime");
            HandlerMethod method = (HandlerMethod) handler;
            log.debug("[TimeHandlerInterceptor]执行{}方法结束，共消耗了{}ms", method.getBeanType() + "." + method.getMethod().getName(), System.currentTimeMillis() - startTime);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
