package com.boco.alarmtitle.client;


import com.boco.domain.DispMessageEntity;
import com.boco.xdpp.model.alarm.exports.beans.DynamicMessageMap;

import java.util.List;
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

    /**
     * 收到消息处理
     * @param dispMessageEntity
     */
    public void receive(List<Integer> filterIdList, DispMessageEntity dispMessageEntity) {
        System.err.println(dispMessageEntity.toString());
    }
}