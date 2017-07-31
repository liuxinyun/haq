package com.lanwei.haq.spider.dao.web;

import com.lanwei.haq.spider.entity.web.SubjectEntity;
import com.lanwei.haq.spider.entity.web.WebClassEntity;
import com.lanwei.haq.spider.entity.web.WebConfigEntity;
import com.lanwei.haq.spider.entity.web.WebEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @作者：刘新运
 * @日期：2017/8/1 0:00
 * @描述：接口
 */

public interface MysqlDao {

    List<Integer> getAllWebId();

    List<WebEntity> getAllWeb();

    WebEntity getWebById(@Param("id") Integer webId);

    List<SubjectEntity> getAllSubject();

    List<WebClassEntity> getClassByWebId(@Param("webId") Integer webId);

    List<WebConfigEntity> getCurrentConfig();

}
