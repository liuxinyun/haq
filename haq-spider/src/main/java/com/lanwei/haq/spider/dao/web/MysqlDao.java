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

    /**
     * 获取所有正在用的网站id
     * @return
     */
    List<Integer> getAllWebId();

    /**
     * 获取所有已经被删除网站id
     * @return
     */
    List<Integer> getAllWebIdDel();

    /**
     * 获取所有网站
     * @return
     */
    List<WebEntity> getAllWeb();

    /**
     * 根据网站id获取网站
     * @param webId
     * @return
     */
    WebEntity getWebById(@Param("id") Integer webId);

    /**
     * 获取所有主题
     * @return
     */
    List<SubjectEntity> getAllSubject();

    /**
     * 根据网站id获取对应的类别实体
     * @param webId
     * @return
     */
    List<WebClassEntity> getClassByWebId(@Param("webId") Integer webId);

    /**
     * 获取当前配置信息
     * @return
     */
    List<WebConfigEntity> getCurrentConfig();

}
