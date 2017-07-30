package com.lanwei.haq.spider.dao.web;

import com.lanwei.haq.spider.entity.web.WebClassEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @作者：刘新运
 * @日期：2017/7/30 21:47
 * @描述：接口
 */

public interface WebClassDao {

    List<WebClassEntity> getClassByWebId(@Param("webId") Integer webId);

}
