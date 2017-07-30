package com.lanwei.haq.spider.service.web;

import com.lanwei.haq.spider.dao.web.WebConfigDao;
import com.lanwei.haq.spider.entity.web.WebConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网站配置
 *
 * @author liuxinyun
 * @date 2016/12/31 14:42
 */
@Service
public class WebConfigService {

    private final WebConfigDao webConfigDao;

    @Autowired
    public WebConfigService(WebConfigDao webConfigDao) {
        this.webConfigDao = webConfigDao;
    }

    /**
     * 获取当前配置
     * @return
     */
    public Map<String, Integer> getCurrentConfig(){
        Map<String, Integer> map = new HashMap<>();
        List<WebConfigEntity> list = webConfigDao.getCurrentConfig();
        for (WebConfigEntity webConfigEntity : list){
            if (webConfigEntity.getType() == 1){
                map.put("cron", webConfigEntity.getData());
            }else if (webConfigEntity.getType() == 2){
                map.put("thread", webConfigEntity.getData());
            }
        }
        return map;
    }


}