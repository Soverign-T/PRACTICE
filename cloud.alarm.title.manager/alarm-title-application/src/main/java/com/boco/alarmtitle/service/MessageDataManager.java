package com.boco.alarmtitle.service;


import com.boco.alarmtitle.common.cache.DBLoaderProcess;
import com.boco.alarmtitle.common.dao.MessageDataDao;
import com.boco.domain.DispMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author hao 2022/10/29 15:13
 */
@Service
public class MessageDataManager {
    private static MessageDataManager messageDataManager;
    private MessageDataDao messageDataDao;
    private DBLoaderProcess dBLoaderProcess;
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Autowired
    public void setTitleTextCache(DBLoaderProcess DBLoaderProcess) {
        this.dBLoaderProcess = DBLoaderProcess;
    }

    @Autowired
    public void setMessageDao(MessageDataDao messageDataDao) {
        this.messageDataDao = messageDataDao;
    }

    public static synchronized MessageDataManager newInstance() {
        if (messageDataManager == null) {
            messageDataManager = new MessageDataManager();
        }
        return messageDataManager;
    }
//一个缓存类然后 程序初始化的时候加载数据告警标题数据; 接收完消息放入缓存 缓存慢慢批量存入数据库

    /**
     * 收到消息处理
     *
     * @param dispMessageEntity
     */
    public void receive(Map<String, Object> stringsMap, DispMessageEntity dispMessageEntity) {

        // 解析出消息数据中有效字段
        //TODO 时间字段修改
        HashMap<String, Object> params = new HashMap<>();
//        String title_text = (String) stringsMap.get("title_text");
//        String title_text1 = (String) stringsMap.get("title_text");
//        String title_text12 = (String) stringsMap.get("title_text");
//        String title_text123 = (String) stringsMap.get("title_text");
//        String title_text11 = (String) stringsMap.get("title_text");
//        String title_text111 = (String) stringsMap.get("title_text");
//        String title_text121 = (String) stringsMap.get("title_text");
//        String title_text1231 = (String) stringsMap.get("title_text");
        params.put("title_text", stringsMap.get("title_text"));
        params.put("title_text1", stringsMap.get("title_text"));
        params.put("title_text12", stringsMap.get("title_text"));
        params.put("title_text123", stringsMap.get("title_text"));
        params.put("title_text11 ", stringsMap.get("title_text"));
        params.put("title_text111 ", stringsMap.get("title_text"));
        params.put("title_text121 ", stringsMap.get("title_text"));
        params.put("title_text1231", stringsMap.get("title_text"));
        //TODO 1.插入到数据库

        //TODO 2.添加到缓存
        dBLoaderProcess.pushData(params);
        //开启新线程
        CompletableFuture.runAsync(() -> {
            //入库
            messageDataDao.insertBatch(params);
            //添加到缓存
        }, threadPoolExecutor);
    }
}