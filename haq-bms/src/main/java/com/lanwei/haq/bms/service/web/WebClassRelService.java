package com.lanwei.haq.bms.service.web;

import com.lanwei.haq.bms.dao.web.ClassDao;
import com.lanwei.haq.bms.dao.web.WebClassRelDao;
import com.lanwei.haq.bms.dao.web.WebDao;
import com.lanwei.haq.bms.entity.web.WebClassRelEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 网站类别关系service,处理网站原类别和系统类别相关的业务逻辑
 *
 * @author liuxinyun
 * @date 2016/12/31 14:42
 */
@Service
public class WebClassRelService {

    private final WebClassRelDao webClassRelDao;
    private final WebDao webDao;
    private final ClassDao classDao;

    @Autowired
    public WebClassRelService(WebClassRelDao webClassRelDao, WebDao webDao, ClassDao classDao) {
        this.webClassRelDao = webClassRelDao;
        this.webDao = webDao;
        this.classDao = classDao;
    }

    /**
     * 获取所有
     * @return
     */
    public Map<String, Object> getAll(){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("webList", webDao.getAll());
        resultMap.put("classList", classDao.getAll());
        return resultMap;
    }

    /**
     * 获取列表
     */
    public Map<String, Object> getList(WebClassRelEntity webClassRelEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if(null == webClassRelEntity) {
            webClassRelEntity = new WebClassRelEntity();
        }

        int count = webClassRelDao.count(webClassRelEntity);
        if(count > 0){
            resultMap.put("list", webClassRelDao.query(webClassRelEntity));
        }
        resultMap.put("count", count);
        resultMap.put("queryEntity", webClassRelEntity);
        resultMap.put("webList", webDao.getAll());
        resultMap.put("classList", classDao.getAll());
        return resultMap;
    }

    /**
     * 根据id获得
     */
    public Map<String, Object> getById(int id){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        WebClassRelEntity webClassRelEntity = new WebClassRelEntity();
        webClassRelEntity.setId(id);
        List<WebClassRelEntity> list = webClassRelDao.query(webClassRelEntity);
        if(ListUtil.isNotEmpty(list)){
            resultMap.put("webclassrel", list.get(0));
        }
        resultMap.put("webList", webDao.getAll());
        resultMap.put("classList", classDao.getAll());
        return resultMap;
    }

    /**
     * 新增
     */
    public Map<String, Object> insert(WebClassRelEntity webClassRelEntity){
        Map<String, Object> resultMap;
        int flag = webClassRelDao.add(webClassRelEntity);
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
    public Map<String, Object> update(WebClassRelEntity webClassRelEntity){
        webClassRelDao.update(webClassRelEntity);
        return ResponseEnum.SUCCESS.getResultMap();
    }

    /**
     * 删除
     */
    public void delete(WebClassRelEntity webClassRelEntity){
        webClassRelDao.del(webClassRelEntity);
    }


    /**
     * 批量删除
     */
    public Map<String, Object> delBatch(int[] id, int userId) {
        webClassRelDao.delList(id, userId);
        return ResponseEnum.SUCCESS.getResultMap();
    }

}