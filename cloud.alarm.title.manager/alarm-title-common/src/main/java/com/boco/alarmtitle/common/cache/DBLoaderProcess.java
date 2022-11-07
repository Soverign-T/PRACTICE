package com.boco.alarmtitle.common.cache;

import com.boco.alarmtitle.common.dao.MessageDataDao;
import com.boco.domain.PmmpErrorResult;
import com.boco.domain.TfuAlarmTitle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author hao 2022/11/5 9:45
 * 缓存类
 */
public class DBLoaderProcess {
    private final List<Map<String, Object>> titleTextList = new ArrayList<>();
    private final Map<String, TfuAlarmTitle> titleTextMap = new ConcurrentHashMap<>();
    private final Map<String, PmmpErrorResult> pmmpErrorResultMap = new ConcurrentHashMap<>();

    private MessageDataDao messageDataDao;

    @Autowired
    public void setMessageDao(MessageDataDao messageDataDao) {
        this.messageDataDao = messageDataDao;
    }

    public List<Map<String, Object>> getList() {
        return titleTextList;
    }

    public Map<String,TfuAlarmTitle> getMap() {
        return titleTextMap;
    }
    public Map<String,PmmpErrorResult> getMapa() {
        return pmmpErrorResultMap;
    }
    public List<Map<String, Object>> getData() {
        if (getList().size() == 0) {
            init();
        }
        return titleTextList;
    }

    /**
     * 从数据库加载数据到缓存
     */
//    public void init() {
//        List<TfuAlarmTitle> list = messageDataDao.selectAll();
//        Map<String, TfuAlarmTitle> titleMap = getMap();
//        titleMap.clear();
//        titleMap.putAll(list.stream().collect(Collectors.toMap(TfuAlarmTitle::getTitleId, tfuAlarmTitle -> tfuAlarmTitle)));
//        for (Map.Entry<String, TfuAlarmTitle> stringTfuAlarmTitleEntry : titleMap.entrySet()) {
//            System.err.println(stringTfuAlarmTitleEntry.getKey() + "-------" + stringTfuAlarmTitleEntry.getValue());
//        }
//    }
    public void init() {
        List<PmmpErrorResult> list = messageDataDao.selectAlldata();
        Map<String, PmmpErrorResult> titleMap = getMapa();
        titleMap.clear();
        titleMap.putAll(list.stream().collect(Collectors.toMap(PmmpErrorResult::getNeId, pmmpErrorResult -> pmmpErrorResult)));
        for (Map.Entry<String, PmmpErrorResult> stringTfuAlarmTitleEntry : titleMap.entrySet()) {
            System.err.println(stringTfuAlarmTitleEntry.getKey() + "-------" + stringTfuAlarmTitleEntry.getValue());
        }
    }
    /**
     * 从消息队列中获取数据添加到map缓存中
     *
     * @param data
     */
    public synchronized void pushData(HashMap<String, Object> data) {
        titleTextList.add(data);
    }
}
