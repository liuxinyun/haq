package com.lanwei.haq.bms.dao.web;

import com.lanwei.haq.bms.dao.CommonDao;
import com.lanwei.haq.bms.entity.web.WebSeedEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description：
 * @author：liuxinyun
 * @date：2017/12/13 21:07
 */
public interface WebSeedDao extends CommonDao<WebSeedEntity> {
    /**
     * 通过网站id查询该网站所有种子网址
     * @param webId
     * @return
     */
    List<WebSeedEntity> queryByWebId(@Param("webId")int webId);
}
