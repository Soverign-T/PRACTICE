package com.boco.alarmtitle.common.cache;

import com.boco.alarmtitle.common.dao.MessageDao;
import com.boco.domain.Admin;
import com.boco.domain.PmmpErrorResult;
import oracle.net.ns.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author hao 2022/11/2 15:02
 */
public class DispMessageEntityCache {

    private List<String> pmmpErrorResults = new ArrayList<>();

    private MessageDao messageDao;

    @Autowired
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void init() {
        List<String> list = messageDao.selectAll();
        pmmpErrorResults.addAll(list);
    }
    public void insssit() {
        HashMap<String, Admin> map = new HashMap<>();
        Set<String> strings = map.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
        }
    }
}
