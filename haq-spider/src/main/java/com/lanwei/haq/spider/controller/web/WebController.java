package com.lanwei.haq.spider.controller.web;

import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.spider.service.web.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 网站管理Controller
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
@Controller
@RequestMapping(value = "/web")
public class WebController {

    private final WebService webService;

    @Autowired
    public WebController(WebService webService) {
        this.webService = webService;
    }

    /**
     * 查询列表
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map<String, Object> getList(){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("web", webService.getAllWeb());
        return resultMap;
    }

}