package com.lanwei.haq.bms.service.web;

import com.lanwei.haq.bms.dao.web.ClassDao;
import com.lanwei.haq.bms.dao.web.WebDao;
import com.lanwei.haq.bms.dao.web.WebSeedDao;
import com.lanwei.haq.bms.entity.web.WebSeedEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 网站种子service,处理网站种子相关的业务逻辑
 *
 * @author liuxinyun
 * @date 2016/12/13 21:42
 */
@Service
public class WebSeedService {

    @Autowired
    private WebSeedDao webSeedDao;
    @Autowired
    private WebDao webDao;
    @Autowired
    private ClassDao classDao;


    /**
     * 获取网站列表
     */
    public Map<String, Object> getList(WebSeedEntity webSeedEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if(null == webSeedEntity) {
            webSeedEntity = new WebSeedEntity();
        }

        int count = webSeedDao.count(webSeedEntity);
        if(count > 0){
            List<WebSeedEntity> query = webSeedDao.query(webSeedEntity);
            for (WebSeedEntity seedEntity : query) {
                dealClass(seedEntity);
            }
            resultMap.put("list", query);
        }
        resultMap.put("count", count);
        resultMap.put("queryEntity", webSeedEntity);
        resultMap.put("webList", webDao.getAll());
        return resultMap;
    }

    /**
     * 根据id获得网站
     */
    public WebSeedEntity getById(int id){
        WebSeedEntity webSeedEntity = new WebSeedEntity();
        webSeedEntity.setId(id);
        List<WebSeedEntity> list = webSeedDao.query(webSeedEntity);
        if(ListUtil.isNotEmpty(list)){
            WebSeedEntity seedEntity = list.get(0);
            dealClass(seedEntity);
            return seedEntity;
        }
        return null;
    }

    /**
     * 新增网站
     */
    public Map<String, Object> insert(WebSeedEntity webSeedEntity){
        Map<String, Object> resultMap;
        int[] classIds = webSeedEntity.getClassIds();
        StringBuilder sb = new StringBuilder();
        for (int classId : classIds) {
            sb.append(classId).append(" ");
        }
        sb.deleteCharAt(sb.length()-1);
        webSeedEntity.setClassId(sb.toString());
        int flag = webSeedDao.add(webSeedEntity);
        if (flag > 0){
            resultMap = ResponseEnum.SUCCESS.getResultMap();
        }else {
            resultMap = ResponseEnum.JDBC_ERROR.getResultMap();
        }
        return resultMap;
    }

    /**
     * 更新网站
     */
    public Map<String, Object> update(WebSeedEntity webSeedEntity){
        int[] classIds = webSeedEntity.getClassIds();
        StringBuilder sb = new StringBuilder();
        for (int classId : classIds) {
            sb.append(classId).append(" ");
        }
        sb.deleteCharAt(sb.length()-1);
        webSeedEntity.setClassId(sb.toString());
        webSeedDao.update(webSeedEntity);
        return ResponseEnum.SUCCESS.getResultMap();
    }

    /**
     * 删除网站
     */
    public void delete(WebSeedEntity webSeedEntity){
        webSeedDao.del(webSeedEntity);
    }


    /**
     * 批量删除
     */
    public Map<String, Object> delBatch(int[] id, int userId) {
        webSeedDao.delList(id, userId);
        return ResponseEnum.SUCCESS.getResultMap();
    }

    /**
     * 处理种子网站多个类别名称显示问题
     */
    private void dealClass(WebSeedEntity webSeedEntity){
        String classId = webSeedEntity.getClassId();
        String[] temp = classId.split(" ");
        int[] classIds = new int[temp.length];
        for (int i=0; i<temp.length; i++){
            classIds[i] = Integer.parseInt(temp[i]);
        }
        List<String> names = classDao.queryByIds(classIds);
        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        webSeedEntity.setClassNames(sb.toString());
        webSeedEntity.setClassIds(classIds);
    }

}