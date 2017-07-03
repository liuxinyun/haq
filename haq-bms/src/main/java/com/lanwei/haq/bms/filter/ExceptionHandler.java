package com.lanwei.haq.bms.filter;

import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.exception.NotLoginException;
import com.lanwei.haq.comm.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyBatchUpdateException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import redis.clients.jedis.exceptions.JedisException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BMS统一异常处理器
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        String logMsg;
        String redirectUrl = "/404";
        ResponseEnum responseEnum = ResponseEnum.DEFAULT_ERROR;
        if (ex instanceof DataAccessException) {
            logMsg = "[数据库发生异常]";
        } else if (ex instanceof JedisException) {
            logMsg = "[jedis发生异常]";
        } else if (ex instanceof PropertyBatchUpdateException) {
            logMsg = "[Spring mvc Bean异常]更新参数的属性发生异常";
            responseEnum = ResponseEnum.PARAM_ERROR;
        } else if (ex instanceof NotLoginException) {
            // 未登录
            logMsg = "用户未登录或者已经登录超时";
            responseEnum = ResponseEnum.NO_LOGIN;
            redirectUrl = "/haq-bms/login";
            ex = null;
        } else {
            logMsg = "[其他类型异常]异常消息：" + ex.getMessage();
        }
        // 获取请求头
        String header = request.getHeader("Accept-Type");

        log.error(logMsg + "，Accept-Type为:" + header, ex);

        // 判断请求是json还是地址跳转
        if (StringUtils.isNotBlank(header) && header.contains("application/json")) {
            response.setContentType("application/json;charset=UTF-8");
            MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
            jsonView.addStaticAttribute("code", responseEnum.getCode());
            jsonView.addStaticAttribute("msg", responseEnum.getMsg());
            return new ModelAndView(jsonView);
        } else {
            return new ModelAndView(new RedirectView(redirectUrl));
        }
    }
}