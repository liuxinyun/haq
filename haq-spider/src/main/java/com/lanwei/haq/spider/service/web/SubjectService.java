package com.lanwei.haq.spider.service.web;

import com.lanwei.haq.spider.dao.web.SubjectDao;
import com.lanwei.haq.spider.dao.web.WebClassDao;
import com.lanwei.haq.spider.entity.web.SubjectEntity;
import com.lanwei.haq.spider.entity.web.WebClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专题信息
 *
 * @author liuxinyun
 * @date 2016/12/31 14:42
 */
@Service
public class SubjectService {

    private final SubjectDao subjectDao;

    @Autowired
    public SubjectService(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    /**
     * 获取所有专题信息
     * @return
     */
    public List<SubjectEntity> getAllSubject(){
        List<SubjectEntity> list = subjectDao.getAll();
        for (SubjectEntity subjectEntity : list){
            String keywords = subjectEntity.getKeywords();
            String[] keywordsArr = keywords.split(",");
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<keywordsArr.length-1; i++){
                sb.append(keywordsArr[i]).append("|");
            }
            sb.append(keywordsArr[keywordsArr.length-1]);
            subjectEntity.setKeywords(sb.toString());
        }
        return list;
    }


}