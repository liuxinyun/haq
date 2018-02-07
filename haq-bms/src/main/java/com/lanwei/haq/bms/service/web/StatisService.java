package com.lanwei.haq.bms.service.web;

import com.lanwei.haq.bms.entity.web.StatisEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.Constant;
import com.lanwei.haq.comm.util.DateUtil;
import com.lanwei.haq.comm.util.EnumDateCode;
import com.lanwei.haq.comm.util.jdbc.MyJedisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Tuple;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description：获取各项统计数据所用
 * @author：liuxinyun
 * @date：2018/1/11 23:05
 */
@Service
public class StatisService {

    @Resource
    private MyJedisService statisJedisService;

    /**
     * 获取网址当天爬虫统计列表
     * @param statisEntity
     * @return
     */
    public Map<String, Object> getList(StatisEntity statisEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if(null == statisEntity) {
            statisEntity = new StatisEntity();
        }
        Date date = DateUtils.addDays(new Date(), -statisEntity.getDayNum());
        String day = DateUtil.formatDate(EnumDateCode.YEARMMDD, date);
        String totalKey = Constant.REDIS_TOTAL + day;
        String failKey = Constant.REDIS_FAIL + day;
        int count = 0;
        List<StatisEntity> query = new ArrayList<>();
        //网址非空表示通过网址精确查
        if (StringUtils.isNotBlank(statisEntity.getUrl())){
            if (null != statisJedisService.zscore(failKey, statisEntity.getUrl())
                    && null != statisJedisService.zscore(totalKey, statisEntity.getUrl())){
                count = 1;
                StatisEntity entity = new StatisEntity();
                entity.setUrl(statisEntity.getUrl());
                entity.setFailCount(statisJedisService.zscore(failKey, statisEntity.getUrl()).intValue());
                entity.setTotalCount(statisJedisService.zscore(totalKey, statisEntity.getUrl()).intValue());
                query.add(entity);
                resultMap.put("list", query);
            }
        }else {
            count = statisJedisService.zcard(failKey).intValue();
            if(count > 0){
                long start = statisEntity.getPageStart();
                long end = statisEntity.getPageStart()+statisEntity.getPageSize()-1;
                if (end >= count){
                    end = -1;
                }
                Set<Tuple> failSet = statisJedisService.zrevrangeWithScores(failKey, start, end);
                Set<Tuple> totalSet = statisJedisService.zrevrangeWithScores(totalKey, 0, -1);
                Map<String, Integer> totalMap = new HashMap<>(totalSet.size());
                for (Tuple tuple : totalSet) {
                    totalMap.put(tuple.getElement(), new Double(tuple.getScore()).intValue());
                }
                for (Tuple tuple : failSet) {
                    StatisEntity entity = new StatisEntity();
                    entity.setUrl(tuple.getElement());
                    entity.setFailCount(new Double(tuple.getScore()).intValue());
                    if (totalMap.containsKey(tuple.getElement())){
                        entity.setTotalCount(totalMap.get(tuple.getElement()));
                    }else {
                        entity.setTotalCount(new Double(tuple.getScore()).intValue());
                    }
                    query.add(entity);
                }
                resultMap.put("list", query);
            }
        }

        resultMap.put("count", count);
        resultMap.put("queryEntity", statisEntity);
        return resultMap;
    }

}
