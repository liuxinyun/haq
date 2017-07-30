package com.lanwei.haq.spider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String index() {
        return "index";
    }

}
