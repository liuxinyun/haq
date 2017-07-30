package com.lanwei.haq.spider.service.web;

import com.lanwei.haq.spider.dao.web.WebDao;
import com.lanwei.haq.spider.entity.web.WebEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 通过网站id获取网站
     */
    public WebEntity getWebById(Integer webId){
        return webDao.getWebById(webId);
    }

    /**
     * 获取所有网站id
     * @return
     */
    public List<Integer> getAllWebId(){
        return webDao.getAllId();
    }

    /**
     * 获取所有网站
     * @return
     */
    public List<WebEntity> getAllWeb(){
        return webDao.getAllWeb();
    }


}