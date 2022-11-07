//package com.boco.alarmtitle.common.cache;
//
//import com.boco.alarmtitle.common.dao.MessageDataDao;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.*;
//
///**
// * @author hao 2022/11/2 15:02
// */
//
//public class DispMessageEntityCache {
//
//    private final List<Map<String, Object>> data = new ArrayList<>();
//    private MessageDataDao messageDataDao;
//    @Autowired
//    public void setMessageDao(MessageDataDao messageDataDao) {
//        this.messageDataDao = messageDataDao;
//    }
//
//    public List<Map<String, Object>> getList() {
//        return data;
//    }
//
//    public List<Map<String, Object>> getData() {
//        if (getList().size() == 0) {
//            init();
//        }
//        return data;
//    }
//
//    /**
//     * 从数据库加载数据到缓存
//     */
//    public void init() {
//        List<Map<String, Object>> list = messageDataDao.selectAll();
//        List<Map<String, Object>> disMessageCache = getList();
//        disMessageCache.clear();
//        disMessageCache.addAll(list);
//
//        for (Map<String, Object> stringObjectMap : list) {
//            System.err.println(stringObjectMap);
//        }
//    }
//}
