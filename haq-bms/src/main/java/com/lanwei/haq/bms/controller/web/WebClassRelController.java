package com.lanwei.haq.bms.controller.web;

import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.entity.web.ClassEntity;
import com.lanwei.haq.bms.entity.web.WebClassRelEntity;
import com.lanwei.haq.bms.service.web.ClassService;
import com.lanwei.haq.bms.service.web.WebClassRelService;
import com.lanwei.haq.comm.annotation.AddEntity;
import com.lanwei.haq.comm.annotation.CurrentUser;
import com.lanwei.haq.comm.annotation.SysLog;
import com.lanwei.haq.comm.enums.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 网站类别关系管理Controller
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
@Controller
@RequestMapping(value = "/webclassrel")
public class WebClassRelController {

    private final WebClassRelService webClassRelService;

    @Autowired
    public WebClassRelController(WebClassRelService webClassRelService) {
        this.webClassRelService = webClassRelService;
    }

    /**
     * 新增
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SysLog(description = "新增网站类别关系")
    public Map<String, Object> insert(@AddEntity WebClassRelEntity webClassRelEntity){
        return webClassRelService.insert(webClassRelEntity);
    }

    /**
     * 更新
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SysLog(description = "更新网站类别关系")
    public Map<String, Object> update(WebClassRelEntity webClassRelEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == webClassRelEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
            return resultMap;
        }
        webClassRelEntity.setModifier(currentUser.getId());
        resultMap = webClassRelService.update(webClassRelEntity);
        return resultMap;
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SysLog(description = "删除网站类别关系")
    public Map<String, Object> delete(WebClassRelEntity webClassRelEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == webClassRelEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
        } else {
            resultMap = ResponseEnum.SUCCESS.getResultMap();
            webClassRelEntity.setModifier(currentUser.getId());
            webClassRelService.delete(webClassRelEntity);
        }
        return resultMap;
    }

    /**
     * 查询列表
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> getList(WebClassRelEntity webClassRelEntity){
        return webClassRelService.getList(webClassRelEntity);
    }

    /**
     * 查询所有系统分类和网站
     */
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public Map<String, Object> getAll(){
        return webClassRelService.getAll();
    }

    /**
     * 批量删除
     */
    @ResponseBody
    @RequestMapping(value = "/delBatch", method = RequestMethod.POST)
    @SysLog(description = "批量删除网站类别关系")
    public Map<String, Object> delBatch(int[] id, @CurrentUser UserEntity currentUser){
        return webClassRelService.delBatch(id, currentUser.getId());

    }

    /**
     * 通过id查询详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Map<String, Object> getById(@PathVariable("id") int id) {
        return webClassRelService.getById(id);
    }

}