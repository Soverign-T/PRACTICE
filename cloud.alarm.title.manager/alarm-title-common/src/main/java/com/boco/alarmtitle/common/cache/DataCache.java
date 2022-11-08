package com.boco.alarmtitle.common.cache;

import com.boco.alarmtitle.common.dao.MessageDataDao;
import com.boco.alarmtitle.common.event.DataChangeEvent;
import com.boco.domain.PmmpErrorResult;
import com.boco.domain.TfuAlarmTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Struct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author hao 2022/11/2 15:02
 */
@Component
public class DataCache {

    public static Map<String, TfuAlarmTitle> receiveMap = new ConcurrentHashMap<>();
    public static Map<String, Object> params = new ConcurrentHashMap<>();
    public static Map<String, TfuAlarmTitle> dataBaseSyncMap = new ConcurrentHashMap<>();
    private MessageDataDao messageDataDao;
    private static Long lastUpdateTime = 0L;

    private static int a = 0;
    @Autowired
    public void setMessageDataDao(MessageDataDao messageDataDao) {
        this.messageDataDao = messageDataDao;
    }


    public Map<String, TfuAlarmTitle> getReceiveMap() {
        return receiveMap;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Map<String, TfuAlarmTitle> getDataBaseSyncMap() {
        return dataBaseSyncMap;
    }

    /**
     * 初始化加载数据
     */
    public void initData() {
        List<TfuAlarmTitle> list = messageDataDao.selectAll();
        Map<String, TfuAlarmTitle> dataBaseSyncMap1 = getDataBaseSyncMap();
        dataBaseSyncMap1.clear();
        dataBaseSyncMap1.putAll(list.stream().collect(Collectors.toMap(TfuAlarmTitle::getTitleId, tfuAlarmTitle -> tfuAlarmTitle)));
        for (Map.Entry<String, TfuAlarmTitle> stringTfuAlarmTitleEntry : dataBaseSyncMap1.entrySet()) {
            System.err.println(stringTfuAlarmTitleEntry.getKey() + "-------" + stringTfuAlarmTitleEntry.getValue());
        }
    }
    @EventListener
    @Async
    public void onEvent(DataChangeEvent event) {
        List<TfuAlarmTitle> list = receiveMap.values().stream().filter(c -> {
            if (c.getTimeStamp() > lastUpdateTime - 100) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        messageDataDao.insertBatch(list);
    }
}