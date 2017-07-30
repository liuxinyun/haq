package com.lanwei.haq.spider.dao.web;

import com.lanwei.haq.spider.entity.web.WebConfigEntity;

import java.util.List;

/**
 * @作者：刘新运
 * @日期：2017/7/30 22:05
 * @描述：接口
 */

public interface WebConfigDao {

    List<WebConfigEntity> getCurrentConfig();

}
