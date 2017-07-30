package com.lanwei.haq.spider.service.web;

import com.lanwei.haq.spider.dao.web.WebDao;
import com.lanwei.haq.spider.entity.web.WebEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 网站service,处理网站相关的业务逻辑
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
    public Map<String, Object> getList(WebEntity webEntity){
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


}