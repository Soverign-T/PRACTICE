package com.boco.alarmtitle.client;


import com.boco.domain.DispMessageEntity;

/**
 * @author hao 2022/10/29 15:13
 */
public class SubClientManager {
    private static SubClientManager subClientManager;
    public static synchronized SubClientManager newInstance(){
        if (subClientManager == null){
            subClientManager = new SubClientManager();
        }
        return subClientManager;
    }

    public void receive(DispMessageEntity dispMessageEntity) {
        System.err.println(dispMessageEntity.toString());
    }
}
