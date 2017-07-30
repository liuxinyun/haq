package com.lanwei.haq.spider.controller.web;

import com.lanwei.haq.comm.util.Constant;
import com.lanwei.haq.comm.util.RedisUtil;
import com.lanwei.haq.spider.entity.web.WebEntity;
import com.lanwei.haq.spider.service.web.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

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
    private final RedisUtil redisUtil;

    @Autowired
    public WebController(WebService webService, RedisUtil redisUtil) {
        this.webService = webService;
        this.redisUtil = redisUtil;
    }

    /**
     * 查询列表
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> getList(WebEntity webEntity){
        Map<String, Object> resultMap = webService.getList(webEntity);
        Jedis jedis = redisUtil.getJedis(Constant.REDIS_SAVE_INDEX);
        jedis.set("Test", "test");
        redisUtil.close(jedis);
        return resultMap;
    }

}