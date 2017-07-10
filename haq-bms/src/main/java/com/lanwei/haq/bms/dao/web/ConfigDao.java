package com.lanwei.haq.bms.dao.web;

import com.lanwei.haq.bms.entity.web.ConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @作者：刘新运
 * @日期：2017/7/9 21:55
 * @描述：接口
 */

public interface ConfigDao {

    /**
     * 所有状态置0
     */
    void update0();
    /**
     * 把指定id状态置1
     * @param ids
     */
    void update1(@Param("ids") int[] ids);

    /**
     * 查询所有的配置项
     * @return
     */
    List<ConfigEntity> query();

}
