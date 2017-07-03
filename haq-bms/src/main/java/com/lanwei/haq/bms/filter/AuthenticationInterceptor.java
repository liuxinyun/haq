package com.lanwei.haq.bms.filter;

import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.comm.exception.NotLoginException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户权限校验拦截器,未登录用户会被拦截
 * @author liuxinyun
 * @date 2016年1月22日 下午5:45:42
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView mv) {

    }

    /**
     * 拦截未登录异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws IOException {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if (null == user) {
            throw new NotLoginException("您还未登录或者已经登录超时");
        }
        return true;
    }
}