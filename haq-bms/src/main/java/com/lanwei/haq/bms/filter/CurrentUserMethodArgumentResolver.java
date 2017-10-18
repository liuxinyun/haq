package com.lanwei.haq.bms.filter;

import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.comm.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用于controller注解获取当前用户
 *
 * @author liuxinyun
 * @created 2016/12/26 12:17
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 用于自动注入新增的实体,实体参数加入CurrentUser会执行该方法，
     * ps：注解类型需加入注解@Target(ElementType.PARAMETER) @Retention(RetentionPolicy.RUNTIME)
     *
     * @return 如果返回true则会执行下面的 {@link #resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)} 方法
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(CurrentUser.class) != null;
    }

    /**
     * 返回当前用户
     *
     * @return 返回当前的用户，如果没有登录则返回为空
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (parameter.getParameterType().getName().equals(UserEntity.class.getName())) {
            return webRequest.getAttribute("user", NativeWebRequest.SCOPE_SESSION);
        }

        return null;
    }
}
