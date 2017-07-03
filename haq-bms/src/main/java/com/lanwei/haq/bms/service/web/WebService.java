package com.lanwei.haq.bms.service.web;

import com.lanwei.haq.bms.dao.web.WebDao;
import com.lanwei.haq.bms.entity.web.WebEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户service,处理用户相关的业务逻辑
 *
 * @author liuxinyun
 * @date 2016/12/31 14:42
 */
@Service
public class WebService {

    private final WebDao webDao;

    @Autowired
    public WebService(WebDao webDao) {
        this.webDao = webDao;
    }

    /**
     * 获取网站数量
     * @return
     */
    public int getWebCount(){
        WebEntity webEntity = new WebEntity();
        int count = webDao.count(webEntity);
        return count;
    }

    /**
     * 获取网站列表
     */
    public Map<String, Object> getWebList(WebEntity webEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if(null == webEntity) {
            webEntity = new WebEntity();
        }

        int count = webDao.count(webEntity);
        if(count > 0){
            resultMap.put("list", webDao.query(webEntity));
        }
        resultMap.put("count", count);
        resultMap.put("queryEntity", webEntity);
        return resultMap;
    }

    /**
     * 根据id获得网站
     */
    public WebEntity getWebById(int id){
        WebEntity webEntity = new WebEntity();
        webEntity.setId(id);
        List<WebEntity> list = webDao.query(webEntity);
        if(ListUtil.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增网站
     */
    public Map<String, Object> insertWeb(WebEntity webEntity){
        Map<String, Object> resultMap;
        int flag = webDao.add(webEntity);
        if (flag > 0){
            resultMap = ResponseEnum.SUCCESS.getResultMap();
        }else {
            resultMap = ResponseEnum.JDBC_ERROR.getResultMap();
        }
        return resultMap;
    }

    /**
     * 更新网站
     */
    public Map<String, Object> updateWeb(WebEntity webEntity){
        webDao.update(webEntity);
        return ResponseEnum.SUCCESS.getResultMap();
    }

    /**
     * 删除网站
     */
    public void deleteWeb(WebEntity webEntity){
        webDao.del(webEntity);
    }


    /**
     * 批量删除
     */
    public Map<String, Object> deleteList(int[] id, int userId) {
        webDao.delList(id, userId);
        return ResponseEnum.SUCCESS.getResultMap();
    }

}