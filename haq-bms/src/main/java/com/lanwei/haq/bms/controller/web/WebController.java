package com.lanwei.haq.bms.controller.web;

import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.entity.web.WebEntity;
import com.lanwei.haq.bms.service.web.WebService;
import com.lanwei.haq.comm.annotation.AddEntity;
import com.lanwei.haq.comm.annotation.CurrentUser;
import com.lanwei.haq.comm.annotation.SysLog;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.HttpConnectionUtil;
import com.lanwei.haq.comm.util.HttpUtil;
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
@RequestMapping(value = "/web")
public class WebController {

    private final WebService webService;

    @Autowired
    public WebController(WebService webService) {
        this.webService = webService;
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
        return webService.getWebList(webEntity);
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
    @RequestMapping(value = "/now", method = RequestMethod.POST)
    public Map<String, Object> spiderByNow() {
        int code = HttpUtil.get("http://172.16.1.12:28080/spider?code=101");
        if (code == 200){
            return ResponseEnum.SUCCESS.getResultMap();
        }
        return ResponseEnum.SYSTEM_ERROR.getResultMap();
    }

}