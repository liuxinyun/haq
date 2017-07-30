package com.lanwei.haq.spider.dao.web;

import com.lanwei.haq.spider.dao.CommonDao;
import com.lanwei.haq.spider.entity.web.WebEntity;

import java.util.List;

/**
 * @作者：刘新运
 * @日期：2017/6/25 22:29
 * @描述：类
 */

public interface WebDao extends CommonDao<WebEntity>{

    List<WebEntity> getAll();

}
