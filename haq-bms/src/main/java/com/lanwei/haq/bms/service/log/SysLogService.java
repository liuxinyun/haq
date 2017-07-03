package com.lanwei.haq.bms.service.log;

import com.lanwei.haq.bms.dao.log.SysLogDao;
import com.lanwei.haq.bms.entity.log.SysLogEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @作者: liuxinyun
 * @日期: 2017/6/15 14:53
 * @描述: 类文件
 */
@Service
public class SysLogService {

    private final SysLogDao sysLogDao;

    @Autowired
    public SysLogService(SysLogDao sysLogDao){
        this.sysLogDao = sysLogDao;
    }

    /**
     * 根据id查询日志
     * @param id
     * @return
     */
    public SysLogEntity getLogById(int id){
        SysLogEntity sysLogEntity = new SysLogEntity();
        sysLogEntity.setId(id);
        List<SysLogEntity> list = sysLogDao.query(sysLogEntity);
        return ListUtil.isNotEmpty(list) ? list.get(0) : null;
    }

    /**
     * 新增日志
     * @param sysLogEntity
     * @return
     */
    public int add(SysLogEntity sysLogEntity) {
        return sysLogDao.add(sysLogEntity);
    }

    /**
     * 删除日志
     * @param sysLogEntity
     * @return
     */
    public void del(SysLogEntity sysLogEntity) {
        sysLogDao.del(sysLogEntity);
    }

    /**
     * 查询所有日志,带分页
     *
     * @param sysLogEntity
     * @return
     */
    public Map<String, Object> logList(SysLogEntity sysLogEntity) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();

        if(null == sysLogEntity)
            sysLogEntity = new SysLogEntity();

        // 查询总数
        int count = sysLogDao.count(sysLogEntity);
        sysLogEntity.setCount(count);

        // 没有查询到则不执行下面的查询
        if (count > 0) {
            resultMap.put("list", sysLogDao.query(sysLogEntity));
        }

        resultMap.put("count", count);
        resultMap.put("queryEntity", sysLogEntity);
        return resultMap;
    }

    /**
     * 批量删除日志
     * @param id
     * @return
     */
    public void delBatch(int[] id) {
        sysLogDao.delList(id, 0);
    }

}
