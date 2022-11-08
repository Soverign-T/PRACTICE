package com.boco.alarmtitle.service;


import com.boco.alarmtitle.common.cache.DBLoaderProcess;
import com.boco.alarmtitle.common.cache.DataCache;
import com.boco.alarmtitle.common.dao.MessageDataDao;
import com.boco.alarmtitle.common.event.DataChangeEvent;
import com.boco.alarmtitle.common.util.SpringContextUtils;
import com.boco.domain.DispMessageEntity;
import com.boco.domain.TfuAlarmTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


/**
 * @author hao 2022/10/29 15:13
 */
@Service
public class MessageDataManager {
    private static MessageDataManager messageDataManager;
    private MessageDataDao messageDataDao;
    private DBLoaderProcess dBLoaderProcess;
    private DataCache dataCache;

    @Autowired
    public void setDataCache(DataCache dataCache) {
        this.dataCache = dataCache;
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
     * @param
     */
    public void receive(Map<String, Object> stringsMap, DispMessageEntity dispMessageEntity) {

        Map<String, TfuAlarmTitle> receiveMap = dataCache.getReceiveMap();
        List<TfuAlarmTitle> resultList= dataCache.getList();
        TfuAlarmTitle TfuAlarmTitle = new TfuAlarmTitle();
        TfuAlarmTitle.setTitleId((String) stringsMap.get(""));
        TfuAlarmTitle.setTitle((String) stringsMap.get(""));
        resultList.add(TfuAlarmTitle);
        Map<String, TfuAlarmTitle> collectMap = resultList.stream().collect(Collectors.toMap(com.boco.domain.TfuAlarmTitle::getTitleId, TfuAlarmTitle1 -> TfuAlarmTitle1));
        receiveMap.putAll(collectMap);
        SpringContextUtils.getApplicationContext().publishEvent(new DataChangeEvent());
    }
}