package com.lanwei.haq.bms.service.web;

import com.lanwei.haq.bms.dao.web.ClassDao;
import com.lanwei.haq.bms.entity.web.ClassEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 网站类别service,处理网站类别相关的业务逻辑
 *
 * @author liuxinyun
 * @date 2016/12/31 14:42
 */
@Service
public class ClassService {

    private final ClassDao classDao;

    @Autowired
    public ClassService(ClassDao classDao) {
        this.classDao = classDao;
    }

    /**
     * 获取所有
     * @return
     */
    public List<ClassEntity> getAll(){
        return classDao.getAll();
    }

    /**
     * 获取列表
     */
    public Map<String, Object> getList(ClassEntity classEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if(null == classEntity) {
            classEntity = new ClassEntity();
        }

        int count = classDao.count(classEntity);
        if(count > 0){
            resultMap.put("list", classDao.query(classEntity));
        }
        resultMap.put("count", count);
        resultMap.put("queryEntity", classEntity);
        return resultMap;
    }

    /**
     * 根据id获得
     */
    public ClassEntity getById(int id){
        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(id);
        List<ClassEntity> list = classDao.query(classEntity);
        if(ListUtil.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增
     */
    public Map<String, Object> insert(ClassEntity classEntity){
        Map<String, Object> resultMap;
        int flag = classDao.add(classEntity);
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
    public Map<String, Object> update(ClassEntity classEntity){
        classDao.update(classEntity);
        return ResponseEnum.SUCCESS.getResultMap();
    }

    /**
     * 删除
     */
    public void delete(ClassEntity classEntity){
        classDao.del(classEntity);
    }


    /**
     * 批量删除
     */
    public Map<String, Object> delBatch(int[] id, int userId) {
        classDao.delList(id, userId);
        return ResponseEnum.SUCCESS.getResultMap();
    }

}