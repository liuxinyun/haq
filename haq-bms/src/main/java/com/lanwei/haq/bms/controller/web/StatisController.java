package com.lanwei.haq.bms.controller.web;

import com.lanwei.haq.bms.entity.web.DayEntity;
import com.lanwei.haq.bms.entity.web.StatisEntity;
import com.lanwei.haq.bms.service.web.StatisService;
import com.lanwei.haq.comm.util.DateUtil;
import com.lanwei.haq.comm.util.EnumDateCode;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 统计查看Controller
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
@RestController
@RequestMapping(value = "/statis")
public class StatisController {

    private static final Logger logger = LoggerFactory.getLogger(StatisController.class);

    private final StatisService statisService;

    @Autowired
    public StatisController(StatisService statisService) {
        this.statisService = statisService;
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> getList(StatisEntity statisEntity){
        Map<String, Object> resultMap = statisService.getList(statisEntity);
        List<DayEntity> dayList = new ArrayList<>(4);
        DayEntity dayEntity = null;
        for (int i=1; i<5; i++){
            dayEntity = new DayEntity();
            dayEntity.setNum(i);
            Date date = DateUtils.addDays(new Date(), -i);
            String day = DateUtil.formatDate(EnumDateCode.YEAR_MM_DD, date);
            dayEntity.setDate(day);
            dayList.add(dayEntity);
        }
        resultMap.put("dayList", dayList);
        return resultMap;
    }

}