package com.lanwei.haq.spider.controller.web;

import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.SpiderUtil;
import com.lanwei.haq.spider.entity.web.WebEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 爬虫管理Controller
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
@Controller
@RequestMapping(value = "/spider")
public class SpiderController {

    private static final Logger logger = LoggerFactory.getLogger(SpiderController.class);

    private  SpiderUtil spiderUtil;

    @Autowired
    public SpiderController(SpiderUtil spiderUtil) {
        this.spiderUtil = spiderUtil;
    }

    @ResponseBody
    @RequestMapping(value = "/now", method = RequestMethod.POST)
    public Map<String, Object> spiderNow(@RequestParam(value = "id") Integer id){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if (id == null){
            logger.error("http param is null");
            resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
            return resultMap;
        }
        logger.info("webId={} will spider.", id);
        if (id == 0){
            spiderUtil.spiderAll();
        }else{
            spiderUtil.spiderByWebId(id);
        }
        return resultMap;
    }

    /**
     * 爬虫测试接口
     * @param webEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Map<String, Object> spiderTest(WebEntity webEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if (webEntity == null || StringUtils.isBlank(webEntity.getWeburl())
                || StringUtils.isBlank(webEntity.getTitleSelect())
                || StringUtils.isBlank(webEntity.getContentSelect())
                || StringUtils.isBlank(webEntity.getRegex())){
            logger.error("spider test param is error");
            resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
            return resultMap;
        }
        // 调用爬虫测试函数
        System.out.println(webEntity.toString());
        resultMap.put("param", "我是返回结果");
        return resultMap;
    }

}