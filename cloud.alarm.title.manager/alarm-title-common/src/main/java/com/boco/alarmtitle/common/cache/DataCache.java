package com.boco.alarmtitle.common.cache;

import com.boco.alarmtitle.common.dao.MessageDataDao;
import com.boco.alarmtitle.common.event.DataChangeEvent;
import com.boco.alarmtitle.common.event.RefreshDataSyncMapEvent;
import com.boco.alarmtitle.common.util.SpringContextUtils;
import com.boco.domain.PmmpErrorResult;
import com.boco.domain.TfuAlarmTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
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

    public static Map<Integer, TfuAlarmTitle> receiveMap = new ConcurrentHashMap<>();
    public static Map<Integer, TfuAlarmTitle> dataBaseSyncMap = new ConcurrentHashMap<>();
    private boolean isInit;//首次是不是加载完成,程序启动的时候
    private MessageDataDao messageDataDao;
    private static Long lastUpdateTime = 0L;

    @Autowired
    public void setMessageDataDao(MessageDataDao messageDataDao) {
        this.messageDataDao = messageDataDao;
    }

    /**
     * 初始化加载数据
     */
    public void initData() {
        List<TfuAlarmTitle> tfuAlarmTitleList = messageDataDao.selectAll();
        dataBaseSyncMap.clear();
        dataBaseSyncMap.putAll(tfuAlarmTitleList.stream().collect(Collectors.toMap(TfuAlarmTitle::getTitleId, tfuAlarmTitle -> tfuAlarmTitle)));
        for (Map.Entry<Integer, TfuAlarmTitle> stringTfuAlarmTitleEntry : dataBaseSyncMap.entrySet()) {
            System.err.println(stringTfuAlarmTitleEntry.getKey() + "-------" + stringTfuAlarmTitleEntry.getValue());
        }
        System.err.println("databaseSyncMap数量====>"+dataBaseSyncMap.size());
    }

    private void batchInsert() {
        System.err.println("databaseSyncMap数量====>"+dataBaseSyncMap.size());
        System.err.println("receiveMap数量====>"+receiveMap.size());
        List<TfuAlarmTitle> list = receiveMap.values().stream().filter(tfuAlarmTitle -> {
            if (dataBaseSyncMap.containsKey(tfuAlarmTitle.getTitleId())) {
                //判断出需要入库的数据
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        for (TfuAlarmTitle tfuAlarmTitle : list) {
            System.err.println(tfuAlarmTitle.getTitleId());
        }
        if (list.size() != 0) {
            messageDataDao.updateBatch(list);
        }

        List<TfuAlarmTitle> list1 = receiveMap.values().stream().filter(tfuAlarmTitle -> {
            if (!dataBaseSyncMap.containsKey(tfuAlarmTitle.getTitleId())) {
                //判断出需要入库的数据
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        for (TfuAlarmTitle tfuAlarmTitle : list1) {
            System.err.println(tfuAlarmTitle.getTitleId());
        }
        messageDataDao.insertBatch(list1);
        SpringContextUtils.getApplicationContext().publishEvent(new RefreshDataSyncMapEvent());
    }

    @EventListener
    @Async
    public void onEvent(DataChangeEvent event) {
        batchInsert();
    }

    @EventListener
    @Async
    public void onEvent(RefreshDataSyncMapEvent event) {
        initData();
    }

    @EventListener
    @Async
    public void onEvent(ContextRefreshedEvent event) {
        synchronized (dataBaseSyncMap) {
            if (!isInit) {
                initData();
                isInit = true;
            }
        }
    }
}
