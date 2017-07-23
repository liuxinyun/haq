package com.lanwei.haq.bms.service.web;

import com.lanwei.haq.bms.dao.web.AreaDao;
import com.lanwei.haq.bms.entity.web.AreaEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 地域service,处理地域相关的业务逻辑
 *
 * @author liuxinyun
 * @date 2016/12/31 14:42
 */
@Service
public class AreaService {

    private final AreaDao areaDao;

    @Autowired
    public AreaService(AreaDao areaDao) {
        this.areaDao = areaDao;
    }

    /**
     * 获取所有
     * @return
     */
    public List<AreaEntity> getAll(){
        return areaDao.getAll();
    }

    /**
     * 获取列表
     */
    public Map<String, Object> getList(AreaEntity areaEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if(null == areaEntity) {
            areaEntity = new AreaEntity();
        }

        int count = areaDao.count(areaEntity);
        if(count > 0){
            resultMap.put("list", areaDao.query(areaEntity));
        }
        resultMap.put("count", count);
        resultMap.put("queryEntity", areaEntity);
        return resultMap;
    }

    /**
     * 根据id获得
     */
    public AreaEntity getById(int id){
        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setId(id);
        List<AreaEntity> list = areaDao.query(areaEntity);
        if(ListUtil.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增
     */
    public Map<String, Object> insert(AreaEntity areaEntity){
        Map<String, Object> resultMap;
        int flag = areaDao.add(areaEntity);
        if (flag > 0){
            resultMap = ResponseEnum.SUCCESS.getResultMap();
        }else {
            resultMap = ResponseEnum.JDBC_ERROR.getResultMap();
        }
        return resultMap;
    }

    /**
     * 更新
     */
    public Map<String, Object> update(AreaEntity areaEntity){
        areaDao.update(areaEntity);
        return ResponseEnum.SUCCESS.getResultMap();
    }

    /**
     * 删除
     */
    public void delete(AreaEntity areaEntity){
        areaDao.del(areaEntity);
    }


    /**
     * 批量删除
     */
    public Map<String, Object> delBatch(int[] id, int userId) {
        areaDao.delList(id, userId);
        return ResponseEnum.SUCCESS.getResultMap();
    }

}