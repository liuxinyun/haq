package com.lanwei.haq.bms.filter;

import com.lanwei.haq.bms.entity.BaseEntity;
import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.comm.annotation.AddEntity;
import com.lanwei.haq.comm.enums.IsDelEnum;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Date;

/**
 * 用于拦截controller的参数，进行自动赋值
 *
 * @author liuxinyun
 * @created 2016/12/26 11:25
 */
public class AddMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 用于自动注入新增的实体,实体参数加入AddEntity会执行该方法，
     * ps：注解类型需加入注解@Target(ElementType.PARAMETER) @Retention(RetentionPolicy.RUNTIME)
     *
     * @return 如果返回true则会执行下面的 {@link #resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)} 方法
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(AddEntity.class) != null;
    }

    /**
     * 用于自动注入新增的公共实体,默认会注入创建时间和修改时间以及isDel，如果存在当前用户，则会插入修改人和创建人
     *
     * @return 返回的结果会作为Controller里参数的值
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object arg = methodParameter.getParameterType().newInstance();
        // 如果改实体继承了BaseEntity则会去注入
        if (arg instanceof BaseEntity) {
            // 构建一个BeanWrapper，用于构建返回的实体
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(arg);
            wrapper.setPropertyValues(nativeWebRequest.getParameterMap());
            wrapper.setPropertyValue("isDel", IsDelEnum.F.getCode());
            // 注入修改时间和创建时间
            Date date = new Date();
            wrapper.setPropertyValue("created", date);
            wrapper.setPropertyValue("modified", date);
            // 注入创建人和修改人
            UserEntity user = (UserEntity) nativeWebRequest.getAttribute("user", NativeWebRequest.SCOPE_SESSION);
            if (null != user) {
                wrapper.setPropertyValue("creater", user.getId());
                wrapper.setPropertyValue("modifier", user.getId());
            }
            return wrapper.getWrappedInstance();
        }
        return null;
    }
}
