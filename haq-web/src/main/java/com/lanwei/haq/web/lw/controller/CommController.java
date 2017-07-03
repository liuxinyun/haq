package com.lanwei.haq.web.lw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @作者：刘新运
 * @日期：2017/6/19 22:34
 * @描述：类
 */
@Controller
public class CommController {

    /**
     * 一级页面跳转
     * @param param
     * @return
     */
    @RequestMapping(value = "/views/{param}", method = RequestMethod.GET)
    public String toView(@PathVariable("param") String param){
        return param;
    }

    /**
     * 二级页面跳转
     * @param param1
     * @param param2
     * @return
     */
    @RequestMapping(value = "/views/{param1}/{param2}", method = RequestMethod.GET)
    public String toView(@PathVariable("param1") String param1, @PathVariable("param2") String param2){
        return param1+"/"+param2;
    }

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public String home(){
        return "home";
    }

}
