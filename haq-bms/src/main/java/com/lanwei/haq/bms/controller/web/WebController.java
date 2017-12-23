package com.lanwei.haq.bms.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.entity.web.ConfigEntity;
import com.lanwei.haq.bms.entity.web.WebEntity;
import com.lanwei.haq.bms.service.web.AreaService;
import com.lanwei.haq.bms.service.web.ClassService;
import com.lanwei.haq.bms.service.web.ConfigService;
import com.lanwei.haq.bms.service.web.WebService;
import com.lanwei.haq.comm.annotation.AddEntity;
import com.lanwei.haq.comm.annotation.CurrentUser;
import com.lanwei.haq.comm.annotation.SysLog;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    private final WebService webService;
    private final ConfigService configService;
    private final AreaService areaService;
    private final ClassService classService;

    @Autowired
    public WebController(WebService webService, ConfigService configService,
                         AreaService areaService, ClassService classService) {
        this.webService = webService;
        this.configService = configService;
        this.areaService = areaService;
        this.classService = classService;
    }

    /**
     * 新增
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SysLog(description = "新增网站")
    public Map<String, Object> insert(@AddEntity WebEntity webEntity){
        return webService.insert(webEntity);
    }

    /**
     * 更新
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
        resultMap = webService.update(webEntity);
        return resultMap;
    }

    /**
     * 删除
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
            webService.delete(webEntity);
        }
        return resultMap;
    }

    /**
     * 查询列表
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> getList(WebEntity webEntity){
        Map<String, Object> resultMap = webService.getList(webEntity);
        resultMap.put("areaList", areaService.getAll());
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
     * 批量删除
     */
    @ResponseBody
    @RequestMapping(value = "/delBatch", method = RequestMethod.POST)
    @SysLog(description = "批量删除网站")
    public Map<String, Object> delBatch(int[] id, @CurrentUser UserEntity currentUser){
        if(null==id || id.length==0){
            return ResponseEnum.PARAM_ERROR.getResultMap();
        }
        return webService.delBatch(id, currentUser.getId());

    }

    /**
     * 通过id查询详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Map<String, Object> getById(@PathVariable("id") int id) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("web", webService.getById(id));
        resultMap.put("areaList", areaService.getAll());
        return resultMap;
    }

    /**
     * 获取类别
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClass/{id}", method = RequestMethod.POST)
    public Map<String, Object> getClass(@PathVariable("id") int id) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("webId", id);
        resultMap.put("classList", classService.getAll());
        return resultMap;
    }

    /**
     * 爬虫测试
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/test/{id}", method = RequestMethod.POST)
    public Map<String, Object> testSpider(@PathVariable("id") int id) {
        WebEntity webEntity = webService.getById(id);
        // 参数
        Map<String, String> params = new HashMap<>();
        params.put("weburl", webEntity.getWeburl());
        params.put("titleSelect", webEntity.getTitleSelect());
        params.put("contentSelect", webEntity.getContentSelect());
        params.put("regex", webEntity.getRegex());
        String jsonStr = HttpUtil.postForm(Constant.SPIDER_TEST, params);
        logger.info("http return {}", jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        String code = object.getString("code");
        if (code.equals("200")){
            Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
            resultMap.put("param", object.get("param"));
            return resultMap;
        }
        return ResponseEnum.SYSTEM_ERROR.getResultMap();
    }

    /**
     * 立即开始爬虫
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/now/{id}", method = RequestMethod.POST)
    public Map<String, Object> spiderByNow(@PathVariable("id") int id, @RequestParam(value = "configIds[]",required = false) int[] configIds) {
        if (id == 0){//配置立即生效
            configService.update(configIds);//更改数据库网站配置表
        }
        // 参数
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        String jsonStr = HttpUtil.postForm(Constant.SPIDER_ADDRESS, params);
        logger.info("http return {}", jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        String code = object.getString("code");
        if (code.equals("200")){
            return ResponseEnum.SUCCESS.getResultMap();
        }
        return ResponseEnum.SYSTEM_ERROR.getResultMap();
    }

}