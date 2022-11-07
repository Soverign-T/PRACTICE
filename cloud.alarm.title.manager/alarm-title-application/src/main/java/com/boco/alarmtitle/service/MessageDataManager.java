package com.boco.alarmtitle.service;


import com.boco.alarmtitle.common.dao.MessageDao;
import com.boco.domain.DispMessageEntity;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author hao 2022/10/29 15:13
 */
@Service
public class MessageDataManager {
    private static MessageDataManager messageDataManager;
    private MessageDao messageDao;
    @Autowired
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public static synchronized MessageDataManager newInstance() {
        if (messageDataManager == null) {
            messageDataManager = new MessageDataManager();
        }
        return messageDataManager;
    }


    /**
     * 收到消息处理
     * @param dispMessageEntity
     */
    public void receive(List<Integer> filterIdList, DispMessageEntity dispMessageEntity) {
        //TODO 解析接收消息完成字段批量插入
        System.err.println(dispMessageEntity.toString());
    }
}