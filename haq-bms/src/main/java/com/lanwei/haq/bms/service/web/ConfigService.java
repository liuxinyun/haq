package com.lanwei.haq.bms.service.web;

import com.lanwei.haq.bms.dao.web.ConfigDao;
import com.lanwei.haq.bms.dao.web.WebDao;
import com.lanwei.haq.bms.entity.web.ConfigEntity;
import com.lanwei.haq.bms.entity.web.WebEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxinyun
 * @date 2016/12/31 14:42
 */
@Service
public class ConfigService {

    private final ConfigDao configDao;

    @Autowired
    public ConfigService(ConfigDao configDao) {
        this.configDao = configDao;
    }

    /**
     * 获取配置列表
     */
    public List<ConfigEntity> getList(){
        return configDao.query();
    }

    /**
     * 获取当前所用配置
     */
    public Map<String, Object> getCurrentUse(){
        Map<String, Object> map = new HashMap<>();
        List<ConfigEntity> list = configDao.query();
        for (ConfigEntity configEntity : list){
            if(configEntity.getStatus() == 1){
                if (configEntity.getType() == 1){
                    map.put("cron", configEntity.getData());
                }else {
                    map.put("speed", configEntity.getDesc());
                }
            }
        }
        return map;
    }

    /**
     * 更新网站
     */
    public void update(int[] ids){
        configDao.update0();
        configDao.update1(ids);
    }

}