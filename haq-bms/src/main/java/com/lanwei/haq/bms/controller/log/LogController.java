package com.lanwei.haq.bms.controller.log;

import com.lanwei.haq.bms.entity.log.SysLogEntity;
import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.service.log.SysLogService;
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
 * @作者: liuxinyun
 * @日期: 2017/6/15 16:49
 * @描述:
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {

    private final SysLogService sysLogService;

    @Autowired
    public LogController(SysLogService sysLogService){
        this.sysLogService = sysLogService;
    }

    /**
     * 分页查询日志
     * @param sysLogEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> query(SysLogEntity sysLogEntity){
        return sysLogService.logList(sysLogEntity);
    }

    /**
     * 删除日志
     * @param sysLogEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Map<String, Object> del(SysLogEntity sysLogEntity, @CurrentUser UserEntity userEntity){
        Map<String, Object> resultMap;
        if (userEntity.getRoleType() != 3){
            resultMap = ResponseEnum.NOT_MANAGER.getResultMap();
            return resultMap;
        }
        if (sysLogEntity==null){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
            return resultMap;
        }
        sysLogService.del(sysLogEntity);
        resultMap = ResponseEnum.SUCCESS.getResultMap();
        return resultMap;
    }

    /**
     * 批量删除日志
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delBatch", method = RequestMethod.POST)
    @SysLog(description = "批量删除日志")
    public Map<String, Object> del(int[] id, @CurrentUser UserEntity userEntity){
        if (userEntity.getRoleType() != 3){
            return ResponseEnum.NOT_MANAGER.getResultMap();
        }
        if (null == id || id.length == 0) {
            return ResponseEnum.PARAM_NULL.getResultMap();
        }
        sysLogService.delBatch(id);
        return ResponseEnum.SUCCESS.getResultMap();
    }

    /**
     * 通过id查询机构详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Map<String, Object> getBranchById(@PathVariable("id") int id) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        SysLogEntity sysLogEntity = sysLogService.getLogById(id);
        resultMap.put("log", sysLogEntity);
        return resultMap;
    }

}
