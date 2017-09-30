package com.lanwei.haq.comm.util;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @作者: liuxinyun
 * @日期: 2017/6/28 10:49
 * @描述:
 */
public class EsUtil {

    private static final Logger log = LoggerFactory.getLogger(EsUtil.class);

    //es客户端
    private final TransportClient client;

    public EsUtil(TransportClient client) {
        if (null == client) {
            throw new NullPointerException("参数[client]不能为空");
        }
        this.client = client;
        log.info("[EsUtil]实例化成功");
    }

    /**
     * 释放
     */
    public void close(TransportClient client) {
        if (client==null)
            return;
        client.close();
    }


    /**
     * 插入对象
     * @param indexName
     * @param type
     * @param o
     */
    public void insertObject(String indexName, String type, Object o) {
        IndexResponse response = client
                //prepareIndex(索引库名，索引type,指定id)
                .prepareIndex(indexName, type)
                .setSource(JSONObject.toJSONString(o)).get();
        if (!response.isCreated()) {
            log.error("insert doc content={} faild", JSONObject.toJSONString(o));
        }
    }

    /**
     * 插入Json字符串
     * @param indexName
     * @param type
     * @param str
     * @throws IOException
     */
    public void insertString(String indexName, String type, String str) {
        IndexResponse response = client
                //prepareIndex(索引库名，索引type,指定id)
                .prepareIndex(indexName, type)
                .setSource(str).get();
        if (!response.isCreated()) {
            log.error("insert doc content={} faild", str);
        }
    }

}
