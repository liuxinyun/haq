package com.lanwei.haq.bms.controller.web;

import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.entity.web.ConfigEntity;
import com.lanwei.haq.bms.entity.web.WebEntity;
import com.lanwei.haq.bms.service.web.ConfigService;
import com.lanwei.haq.bms.service.web.WebService;
import com.lanwei.haq.comm.annotation.AddEntity;
import com.lanwei.haq.comm.annotation.CurrentUser;
import com.lanwei.haq.comm.annotation.SysLog;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final ConfigService configService;

    @Autowired
    public WebController(WebService webService, ConfigService configService) {
        this.webService = webService;
        this.configService = configService;
    }

    /**
     * 新增网站
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SysLog(description = "新增网站")
    public Map<String, Object> insertWeb(@AddEntity WebEntity webEntity){
        return webService.insertWeb(webEntity);
    }

    /**
     * 更新网站
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SysLog(description = "更新网站")
    public Map<String, Object> update(WebEntity webEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == webEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
            return resultMap;
        }
        webEntity.setModifier(currentUser.getId());
        resultMap = webService.updateWeb(webEntity);
        return resultMap;
    }

    /**
     * 删除网站
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SysLog(description = "删除网站")
    public Map<String, Object> delete(WebEntity webEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == webEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
        } else {
            resultMap = ResponseEnum.SUCCESS.getResultMap();
            webEntity.setModifier(currentUser.getId());
            webService.deleteWeb(webEntity);
        }
        return resultMap;
    }

    /**
     * 查询用户列表
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> getList(WebEntity webEntity){
        Map<String, Object> resultMap = webService.getWebList(webEntity);
        List<ConfigEntity> list = configService.getList();
        List<ConfigEntity> cron = new ArrayList<>();
        List<ConfigEntity> speed = new ArrayList<>();
        for (ConfigEntity configEntity : list){
            if (configEntity.getType() == 1){
                cron.add(configEntity);
            }else {
                speed.add(configEntity);
            }
        }
        resultMap.put("cron",cron);
        resultMap.put("speed",speed);
        return resultMap;
    }

    /**
     * 批量删除用户
     */
    @ResponseBody
    @RequestMapping(value = "/delWeb", method = RequestMethod.POST)
    @SysLog(description = "批量删除网站")
    public Map<String, Object> delUser(int[] id, @CurrentUser UserEntity currentUser){
        return webService.deleteList(id, currentUser.getId());

    }

    /**
     * 通过id查询详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Map<String, Object> getWebById(@PathVariable("id") int id) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("web", webService.getWebById(id));
        return resultMap;
    }

    /**
     * 立即开始爬虫
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/now/{id}", method = RequestMethod.POST)
    public Map<String, Object> spiderByNow(@PathVariable("id") int id, @RequestParam(value = "configIds[]",required = false) int[] configIds) {
        String key;
        if (id==0){//配置立即生效
            configService.update(configIds);//更改数据库网站配置表
            key = Constant.REDIS_WEBCONFIG_KEY;
        }else {//该网站立即生效
            WebEntity webEntity = webService.getWebById(id);
            String domain = WebUtil.getDomain(webEntity.getWeburl());
            key = Constant.REDIS_WEBSITE_PREFIX+domain+"_"+id;
        }
        Jedis jedis = RedisUtil.getJedis(Constant.REDIS_TCP_INDEX);
        jedis.set(key,"1");
        int i=10;
        boolean flag = false;
        while (i>0){
            if (jedis.get(key).equals("2")){
                flag = true;
                jedis.set(key, "0");//恢复原样
                break;
            }
            i--;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        RedisUtil.close(jedis);
        if (flag){
            return ResponseEnum.SUCCESS.getResultMap();
        }
        return ResponseEnum.SYSTEM_ERROR.getResultMap();
    }

}