package com.lanwei.haq.bms.service.web;

import com.lanwei.haq.bms.dao.web.AreaDao;
import com.lanwei.haq.bms.dao.web.WebDao;
import com.lanwei.haq.bms.dao.web.WebSeedDao;
import com.lanwei.haq.bms.entity.web.AreaEntity;
import com.lanwei.haq.bms.entity.web.WebEntity;
import com.lanwei.haq.bms.entity.web.WebSeedEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.ListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 网站service,处理网站相关的业务逻辑
 *
 * @author liuxinyun
 * @date 2016/12/31 14:42
 */
@Service
public class WebService {

    private final WebDao webDao;
    private final WebSeedDao webSeedDao;

    @Autowired
    public WebService(WebDao webDao, WebSeedDao webSeedDao) {
        this.webDao = webDao;
        this.webSeedDao = webSeedDao;
    }

    /**
     * 获取网站数量
     * @return
     */
    public int getWebCount(){
        WebEntity webEntity = new WebEntity();
        int count = webDao.count(webEntity);
        return count;
    }

    /**
     * 获取所有网站
     * @return
     */
    public List<WebEntity> getAll(){
        return webDao.getAll();
    }

    /**
     * 获取网站列表
     */
    public Map<String, Object> getList(WebEntity webEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if(null == webEntity) {
            webEntity = new WebEntity();
        }

        int count = webDao.count(webEntity);
        if(count > 0){
            List<WebEntity> webEntities = webDao.query(webEntity);
            for (WebEntity entity : webEntities) {
                dealSeed(entity);
            }
            resultMap.put("list", webEntities);
        }
        resultMap.put("count", count);
        resultMap.put("queryEntity", webEntity);
        return resultMap;
    }

    /**
     * 根据id获得网站
     */
    public WebEntity getById(int id){
        WebEntity webEntity = new WebEntity();
        webEntity.setId(id);
        List<WebEntity> list = webDao.query(webEntity);
        if(ListUtil.isNotEmpty(list)){
            WebEntity entity = list.get(0);
            dealSeed(entity);
            return entity;
        }
        return null;
    }

    /**
     * 新增网站
     */
    public Map<String, Object> insert(WebEntity webEntity){
        Map<String, Object> resultMap;
        int flag = webDao.add(webEntity);
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
    public Map<String, Object> update(WebEntity webEntity){
        webDao.update(webEntity);
        return ResponseEnum.SUCCESS.getResultMap();
    }

    /**
     * 删除网站
     */
    public void delete(WebEntity webEntity){
        webDao.del(webEntity);
    }


    /**
     * 批量删除
     */
    public Map<String, Object> delBatch(int[] id, int userId) {
        webDao.delList(id, userId);
        return ResponseEnum.SUCCESS.getResultMap();
    }

    private void dealSeed(WebEntity webEntity){
        List<WebSeedEntity> webSeedEntities = webSeedDao.queryByWebId(webEntity.getId());
        if (CollectionUtils.isEmpty(webSeedEntities)){
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (WebSeedEntity seedEntity : webSeedEntities) {
            sb.append(seedEntity.getSeedurl()).append(":").append(seedEntity.getClassName()).append(",");
        }
        webEntity.setSeedUrls(sb.substring(0, sb.length()-1));
    }

}