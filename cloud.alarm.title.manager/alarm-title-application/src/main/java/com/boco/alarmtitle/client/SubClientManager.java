package com.boco.alarmtitle.client;


import com.boco.domain.DispMessageEntity;
import com.boco.xdpp.model.alarm.exports.beans.DynamicMessageMap;

import java.util.Map;


/**
 * @author hao 2022/10/29 15:13
 */
public class SubClientManager {
    private static SubClientManager subClientManager;

    public static synchronized SubClientManager newInstance() {
        if (subClientManager == null) {
            subClientManager = new SubClientManager();
        }
        return subClientManager;
    }

    public void receive(DynamicMessageMap dynamicMessageMap, DispMessageEntity dispMessageEntity) {
//        for (Map.Entry<String, Object> entry : dynamicMessageMap.getStringMap().entrySet()) {
//            System.err.println(entry.getKey() + "+" + entry.getValue());
//        }
        System.err.println(dispMessageEntity);
    }
}