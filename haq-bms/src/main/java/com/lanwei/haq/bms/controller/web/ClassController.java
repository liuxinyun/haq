package com.lanwei.haq.bms.controller.web;

import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.entity.web.ClassEntity;
import com.lanwei.haq.bms.service.web.ClassService;
import com.lanwei.haq.comm.annotation.AddEntity;
import com.lanwei.haq.comm.annotation.CurrentUser;
import com.lanwei.haq.comm.annotation.SysLog;
import com.lanwei.haq.comm.enums.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 网站类别管理Controller
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
@RestController
@RequestMapping(value = "/webclass")
public class ClassController {

    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SysLog(description = "新增网站类别")
    public Map<String, Object> insert(@AddEntity ClassEntity classEntity){
        return classService.insert(classEntity);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SysLog(description = "更新网站类别")
    public Map<String, Object> update(ClassEntity classEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == classEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
            return resultMap;
        }
        classEntity.setModifier(currentUser.getId());
        resultMap = classService.update(classEntity);
        return resultMap;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SysLog(description = "删除网站类别")
    public Map<String, Object> delete(ClassEntity classEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == classEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
        } else {
            resultMap = ResponseEnum.SUCCESS.getResultMap();
            classEntity.setModifier(currentUser.getId());
            classService.delete(classEntity);
        }
        return resultMap;
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> getList(ClassEntity classEntity){
        return classService.getList(classEntity);
    }

    /**
     * 查询所有系统分类
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public Map<String, Object> getAll(){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("classList", classService.getAll());
        return resultMap;
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/delBatch", method = RequestMethod.POST)
    @SysLog(description = "批量删除网站类别")
    public Map<String, Object> delBatch(int[] id, @CurrentUser UserEntity currentUser){
        if(null==id || id.length==0){
            return ResponseEnum.PARAM_ERROR.getResultMap();
        }
        return classService.delBatch(id, currentUser.getId());

    }

    /**
     * 通过id查询详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Map<String, Object> getById(@PathVariable("id") int id) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("webclass", classService.getById(id));
        return resultMap;
    }

}