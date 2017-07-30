package com.lanwei.haq.spider.service.web;

import com.lanwei.haq.spider.dao.web.WebClassDao;
import com.lanwei.haq.spider.entity.web.WebClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网站分类
 *
 * @author liuxinyun
 * @date 2016/12/31 14:42
 */
@Service
public class WebClassService {

    private final WebClassDao webClassDao;

    @Autowired
    public WebClassService(WebClassDao webClassDao) {
        this.webClassDao = webClassDao;
    }

    /**
     * 根据网站id获取分类信息
     * @return
     */
    public List<WebClassEntity> getClassByWebId(Integer webId){
        return webClassDao.getClassByWebId(webId);
    }


}