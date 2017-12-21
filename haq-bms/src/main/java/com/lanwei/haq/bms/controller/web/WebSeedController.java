package com.lanwei.haq.bms.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.entity.web.WebEntity;
import com.lanwei.haq.bms.entity.web.WebSeedEntity;
import com.lanwei.haq.bms.service.web.ClassService;
import com.lanwei.haq.bms.service.web.WebSeedService;
import com.lanwei.haq.bms.service.web.WebService;
import com.lanwei.haq.comm.annotation.AddEntity;
import com.lanwei.haq.comm.annotation.CurrentUser;
import com.lanwei.haq.comm.annotation.SysLog;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.Constant;
import com.lanwei.haq.comm.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 网站管理Controller
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
@Controller
@RequestMapping(value = "/webseed")
public class WebSeedController {

    private static final Logger logger = LoggerFactory.getLogger(WebSeedController.class);

    private final WebSeedService webSeedService;
    private final WebService webService;
    private final ClassService classService;

    @Autowired
    public WebSeedController(WebSeedService webSeedService, WebService webService,
                             ClassService classService) {
        this.webSeedService = webSeedService;
        this.webService = webService;
        this.classService = classService;
    }

    /**
     * 新增
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SysLog(description = "新增种子网址")
    public Map<String, Object> insert(@AddEntity WebSeedEntity webSeedEntity){
        Map<String, Object> resultMap;
        if(null == webSeedEntity || webSeedEntity.getWebId()==0 || webSeedEntity.getClassIds().length==0){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
            return resultMap;
        }
        // 目前种子不支持配置，使用网站的
        WebEntity webEntity = webService.getById(webSeedEntity.getWebId());
        webSeedEntity.setTitleSelect(webEntity.getTitleSelect());
        webSeedEntity.setContentSelect(webEntity.getContentSelect());
        webSeedEntity.setRegex(webEntity.getRegex());
        return webSeedService.insert(webSeedEntity);
    }

    /**
     * 更新
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SysLog(description = "更新网站")
    public Map<String, Object> update(WebSeedEntity webSeedEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == webSeedEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
            return resultMap;
        }
        WebSeedEntity seedEntity = webSeedService.getById(webSeedEntity.getId());
        if (seedEntity.getWebId() != webSeedEntity.getWebId()){
            // 换新网站，使用新网站的属性
            WebEntity webEntity = webService.getById(webSeedEntity.getWebId());
            webSeedEntity.setTitleSelect(webEntity.getTitleSelect());
            webSeedEntity.setContentSelect(webEntity.getContentSelect());
            webSeedEntity.setRegex(webEntity.getRegex());
        }
        webSeedEntity.setModifier(currentUser.getId());
        resultMap = webSeedService.update(webSeedEntity);
        return resultMap;
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SysLog(description = "删除网站")
    public Map<String, Object> delete(WebSeedEntity webSeedEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == webSeedEntity || webSeedEntity.getId()==0){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
        } else {
            resultMap = ResponseEnum.SUCCESS.getResultMap();
            webSeedEntity.setModifier(currentUser.getId());
            webSeedService.delete(webSeedEntity);
        }
        return resultMap;
    }

    /**
     * 查询列表
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> getList(WebSeedEntity webSeedEntity){
        return webSeedService.getList(webSeedEntity);
    }

    /**
     * 批量删除
     */
    @ResponseBody
    @RequestMapping(value = "/delBatch", method = RequestMethod.POST)
    @SysLog(description = "批量删除种子网址")
    public Map<String, Object> delBatch(int[] id, @CurrentUser UserEntity currentUser){
        return webSeedService.delBatch(id, currentUser.getId());

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
        resultMap.put("web", webSeedService.getById(id));
        resultMap.put("webList", webService.getAll());
        resultMap.put("classList", classService.getAll());
        return resultMap;
    }

    /**
     * 获取所有添加需要的
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allNeed", method = RequestMethod.POST)
    public Map<String, Object> getAllNeed() {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("webList", webService.getAll());
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
        WebSeedEntity webSeedEntity = webSeedService.getById(id);
        // 参数
        Map<String, String> params = new HashMap<>();
        params.put("weburl", webSeedEntity.getSeedurl());
        params.put("titleSelect", webSeedEntity.getTitleSelect());
        params.put("contentSelect", webSeedEntity.getContentSelect());
        params.put("regex", webSeedEntity.getRegex());
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

}