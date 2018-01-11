package com.lanwei.haq.spider.controller.web;

import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.thread.SpiderTest;
import com.lanwei.haq.comm.util.SpiderUtil;
import com.lanwei.haq.spider.entity.web.WebEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 爬虫管理Controller
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
@RestController
@RequestMapping(value = "/spider")
public class SpiderController {

    private static final Logger logger = LoggerFactory.getLogger(SpiderController.class);

    /**
     * 线程池
     */
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource
    private  SpiderUtil spiderUtil;

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
     * 网站、种子网址爬虫测试
     * @param webEntity
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Map<String, Object> webTest(WebEntity webEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if (webEntity == null || StringUtils.isBlank(webEntity.getWeburl())
                || StringUtils.isBlank(webEntity.getTitleSelect())
                || StringUtils.isBlank(webEntity.getContentSelect())
                || StringUtils.isBlank(webEntity.getRegex())){
            logger.error("web spider test param is error");
            resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
            return resultMap;
        }
        // 调用爬虫测试函数
        Future future = taskExecutor.submit(new SpiderTest(webEntity, Boolean.FALSE));
        try {
            Object o = future.get();
            resultMap.put("param", o.toString());
        } catch (Exception e) {
            logger.error("web spider test error and param is {}", webEntity);
            resultMap.put("param", e.getMessage());
        }
        return resultMap;
    }

    /**
     * 单个网址的测试
     * @param webEntity
     * @return
     */
    @RequestMapping(value = "/url/test", method = RequestMethod.POST)
    public Map<String, Object> urlTest(WebEntity webEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if (webEntity == null || StringUtils.isBlank(webEntity.getWeburl())
                || StringUtils.isBlank(webEntity.getTitleSelect())
                || StringUtils.isBlank(webEntity.getContentSelect())){
            logger.error("url spider test param is error");
            resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
            return resultMap;
        }
        // 调用爬虫测试函数
        Future future = taskExecutor.submit(new SpiderTest(webEntity, Boolean.TRUE));
        try {
            Object o = future.get();
            resultMap.put("param", o.toString());
        } catch (Exception e) {
            logger.error("url spider test error and param is {}", webEntity);
            resultMap.put("param", e.getMessage());
        }
        return resultMap;
    }

}