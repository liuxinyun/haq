package com.lanwei.haq.bms.controller;

import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.comm.annotation.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuxinyun
 * @date 2017/1/6 12:59
 */
@Controller
public class CommonController {

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index(@CurrentUser UserEntity userEntity) {
        if (null != userEntity) {
            return "redirect:/main";
        }
        return "login";
    }

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login(@CurrentUser UserEntity userEntity) {
        if (null != userEntity) {
            return "redirect:/main";
        }
        return "login";
    }

    /**
     * 用户主页面
     *
     * @return
     */
    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    /**
     * 退出系统
     *
     * @return
     */
    @RequestMapping("/exit")
    public String exit(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

    /**
     * 二级页面跳转
     * @param param1
     * @param param2
     * @return
     */
    @RequestMapping(value = "/html/{param1}/{param2}")
    public String toView(@PathVariable("param1") String param1, @PathVariable("param2") String param2){
        return param1+"/"+param2;
    }

}
