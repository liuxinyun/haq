package com.lanwei.haq.bms.controller.web;

import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.entity.web.AreaEntity;
import com.lanwei.haq.bms.entity.web.ClassEntity;
import com.lanwei.haq.bms.service.web.AreaService;
import com.lanwei.haq.bms.service.web.ClassService;
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
 * 地域管理Controller
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
@Controller
@RequestMapping(value = "/area")
public class AreaController {

    private final AreaService areaService;

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    /**
     * 新增
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SysLog(description = "新增地域")
    public Map<String, Object> insert(@AddEntity AreaEntity areaEntity){
        return areaService.insert(areaEntity);
    }

    /**
     * 更新
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SysLog(description = "更新地域")
    public Map<String, Object> update(AreaEntity areaEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == areaEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
            return resultMap;
        }
        areaEntity.setModifier(currentUser.getId());
        resultMap = areaService.update(areaEntity);
        return resultMap;
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SysLog(description = "删除地域")
    public Map<String, Object> delete(AreaEntity areaEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == areaEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
        } else {
            resultMap = ResponseEnum.SUCCESS.getResultMap();
            areaEntity.setModifier(currentUser.getId());
            areaService.delete(areaEntity);
        }
        return resultMap;
    }

    /**
     * 查询地域列表
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> getList(AreaEntity areaEntity){
        return areaService.getList(areaEntity);
    }

    /**
     * 查询所有地域
     */
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public Map<String, Object> getAll(){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("areaList", areaService.getAll());
        return resultMap;
    }

    /**
     * 批量删除
     */
    @ResponseBody
    @RequestMapping(value = "/delBatch", method = RequestMethod.POST)
    @SysLog(description = "批量删除地域")
    public Map<String, Object> delBatch(int[] id, @CurrentUser UserEntity currentUser){
        if(null==id || id.length==0){
            return ResponseEnum.PARAM_ERROR.getResultMap();
        }
        return areaService.delBatch(id, currentUser.getId());

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
        resultMap.put("area", areaService.getById(id));
        return resultMap;
    }

}