package com.boco.alarmtitle.common.cache;

import com.boco.alarmtitle.common.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author hao 2022/11/2 15:02
 */

public class DispMessageEntityCache {

//    Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
    private MessageDao messageDao;

    @Autowired
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }


    /**
     * 从数据库加载数据到缓存
     */
    public void init() {
        List<Map<String, Object>> list = messageDao.selectAll();
        for (Map<String, Object> stringObjectMap : list) {
            System.err.println(stringObjectMap);
        }
/*//        concurrentHashMap = list.stream().collect(Collectors.toMap(obj -> String.valueOf(obj), s -> s));
//        for (Map.Entry<String, Object> stringObjectEntry : concurrentHashMap.entrySet()) {
//            System.err.println("key:" + stringObjectEntry.getKey() + "+" + "value:" + stringObjectEntry.getValue());
//        }
    }


    //    public static void main(String[] args) {
//        List<Admin> list = new ArrayList<>();
//
//        Admin admin = new Admin(1,"zhangsan");
//        Admin admin1 = new Admin(2,"zhangsan1");
//        list.add(admin);
//        list.add(admin1);
//        Map<Integer, Admin> map = list.stream().collect(Collectors.toMap(Admin::getId, s -> s));
//        for (Integer integer : map.keySet()) {
//            System.err.println(integer+ ""+map.get(integer));
//        }
//    }
//    public static void initCache() {
//        init();
//    }*/
    }
}
