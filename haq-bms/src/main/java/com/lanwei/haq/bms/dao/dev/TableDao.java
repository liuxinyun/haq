package com.lanwei.haq.bms.dao.dev;

import com.lanwei.haq.bms.dao.CommonDao;
import com.lanwei.haq.bms.entity.dev.TableEntity;
import com.lanwei.haq.bms.entity.dev.TableFieldEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuxinyun
 * @created 2016/12/22 16:00
 */
public interface TableDao extends CommonDao<TableEntity> {

    /**
     * 显示数据库所有的表
     *
     * @return
     */
    List<String> showTables();

    /**
     * 显示表结构
     *
     * @param name
     * @return
     */
    List<TableFieldEntity> desc(@Param("t_name") String name);
}
