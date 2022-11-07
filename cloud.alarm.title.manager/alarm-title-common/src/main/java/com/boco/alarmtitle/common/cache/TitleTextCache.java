package com.boco.alarmtitle.common.cache;

import com.boco.alarmtitle.common.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author hao 2022/11/5 9:45
 */
public class TitleTextCache {
    private final List<Map<String, Object>> data = new ArrayList<>();
    private MessageDao messageDao;
    @Autowired
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public List<Map<String, Object>> getList() {
        return data;
    }

    public List<Map<String, Object>> getData() {
        if (getList().size() == 0) {
            init();
        }
        return data;
    }

    /**
     * 从数据库加载数据到缓存
     */
    public void init() {
        List<Map<String, Object>> list = messageDao.selectAll();
        List<Map<String, Object>> disMessageCache = getList();
        disMessageCache.clear();
        disMessageCache.addAll(list);

        for (Map<String, Object> stringObjectMap : list) {
            System.err.println(stringObjectMap);
        }
    }
}
