package com.lanwei.haq.bms.dao.web;

import com.lanwei.haq.bms.dao.CommonDao;
import com.lanwei.haq.bms.entity.web.ClassEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @作者：刘新运
 * @日期：2017/6/25 22:29
 * @描述：类
 */

public interface ClassDao extends CommonDao<ClassEntity>{

    List<ClassEntity> getAll();

    /**
     * 根据ids获取类别名称
     * @param ids
     * @return
     */
    List<String> queryByIds(@Param("ids") int[] ids);
}
