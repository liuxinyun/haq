package com.lanwei.haq.spider.controller.web;

import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.SpiderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 网站管理Controller
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
@Controller
@RequestMapping(value = "/spider")
public class SpiderController {

    private  SpiderUtil spiderUtil;

    @Autowired
    public SpiderController(SpiderUtil spiderUtil) {
        this.spiderUtil = spiderUtil;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Map<String, Object> spiderNow(@PathVariable("id") int id){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if (id == 0){
            spiderUtil.spiderAll();
        }else{
            spiderUtil.spiderByWebId(id);
        }
        return resultMap;
    }

}