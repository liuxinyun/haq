package com.lanwei.haq.web.lw.service.clazz;

import com.lanwei.haq.web.lw.dao.clazz.ClazzDao;
import com.lanwei.haq.web.lw.entity.clazz.Clazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @作者：刘新运
 * @日期：2017/6/25 16:05
 * @描述：类
 */
@Service
public class ClazzService {

    private final ClazzDao clazzDao;

    @Autowired
    public ClazzService(ClazzDao clazzDao) {
        this.clazzDao = clazzDao;
    }

    public List<Clazz> query(){
        return clazzDao.query();
    }
}
