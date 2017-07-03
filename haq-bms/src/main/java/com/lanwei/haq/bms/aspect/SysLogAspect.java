package com.lanwei.haq.bms.aspect;

import com.lanwei.haq.bms.entity.log.SysLogEntity;
import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.service.log.SysLogService;
import com.lanwei.haq.comm.annotation.SysLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @作者: liuxinyun
 * @日期: 2017/6/15 15:02
 * @描述:
 */
@Aspect
@Component
public class SysLogAspect {

    private final SysLogService sysLogService;

    @Autowired
    public SysLogAspect(SysLogService sysLogService){
        this.sysLogService = sysLogService;
    }

    @Pointcut("@annotation(com.lanwei.haq.comm.annotation.SysLog)")
    public void logAspect(){
    }

    /**
     * 后置通知记录用户操作
     * @param joinPoint
     */
    @After("logAspect()")
    public void doAfter(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //读取session中的用户
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        //获取请求ip
        String ip = request.getRemoteAddr();
        //获取请求路径
        String url = request.getRequestURI();
        try {
            //获取请求方法的参数并转化为字符串
            StringBuffer params = new StringBuffer();
            if(joinPoint.getArgs()!=null && joinPoint.getArgs().length>0){
                for (int i=0; i<joinPoint.getArgs().length; i++){
                    if (joinPoint.getArgs()[i] == null)
                        continue;
                    params.append(joinPoint.getArgs()[i].toString()).append(";");
                }
            }
            SysLogEntity sysLogEntity = new SysLogEntity();
            sysLogEntity.setDescription(getMethodDescription(joinPoint));
            sysLogEntity.setUrl(url);
            sysLogEntity.setParam(params.toString());
            sysLogEntity.setUserId(user.getId());
            sysLogEntity.setUsername(user.getName());
            sysLogEntity.setIp(ip);
            sysLogService.add(sysLogEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取注解中对方法的描述信息
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String getMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method:methods){
            if (method.getName().equals(methodName)){
                Class[] classes = method.getParameterTypes();
                if (classes.length == arguments.length){
                    description = method.getAnnotation(SysLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

}
