package com.boco.alarmtitle.client;


import com.boco.alarmtitle.common.cache.DispMessageEntityCache;
import com.boco.domain.Admin;
import com.boco.domain.DispMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author hao 2022/10/29 15:13
 */
public class SubClientManager {
    private static SubClientManager subClientManager;

    private DispMessageEntityCache dispMessageEntityCache;

    public void setDispMessageEntityCache(DispMessageEntityCache dispMessageEntityCache) {
        this.dispMessageEntityCache = dispMessageEntityCache;
    }

    private HashMap<Integer, Admin> hashMap = new HashMap<>();
    public static List<DispMessageEntity> dispMessageEntityList = new ArrayList<>();
    public static List<Admin> adminList = new ArrayList<>();

    public static synchronized SubClientManager newInstance() {
        if (subClientManager == null) {
            subClientManager = new SubClientManager();
        }
        return subClientManager;
    }

    public void receive(List<Integer> filterIdList, DispMessageEntity dispMessageEntity) {

    }

    public void receive(Admin admin) {
        hashMap.put(admin.getId(), admin);
    }
}
