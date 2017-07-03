package com.lanwei.haq.bms.service.dev;

import com.lanwei.haq.bms.dao.dev.TableDao;
import com.lanwei.haq.bms.entity.dev.TableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表的service，用于显示表结构，新建表之类的操作，
 *
 * @author liuxinyun
 * @created 2016/12/22 15:53
 */
@Service
public class TableService {

    private final TableDao tableMapper;

    @Autowired
    public TableService(TableDao tableMapper) {
        this.tableMapper = tableMapper;
    }

    /**
     * 查询数据库所有的表结构
     *
     * @return List<TableEntity>
     */
    public List<TableEntity> desc() {
        List<TableEntity> rList = new ArrayList<>();
        List<String> tables = tableMapper.showTables();
        TableEntity tableEntity;
        for (String name : tables) {
            tableEntity = new TableEntity(name);
            tableEntity.setList(tableMapper.desc(name));
            rList.add(tableEntity);
        }
        return rList;
    }

}
