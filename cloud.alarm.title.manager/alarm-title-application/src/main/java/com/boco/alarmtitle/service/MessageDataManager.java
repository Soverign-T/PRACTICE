package com.boco.alarmtitle.service;


import com.boco.alarmtitle.common.cache.DataCache;
import com.boco.alarmtitle.common.event.DataChangeEvent;
import com.boco.alarmtitle.common.util.SpringContextUtils;
import com.boco.domain.TfuAlarmTitle;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;


/**
 * @author hao 2022/10/29 15:13
 */
@Service
public class MessageDataManager {
    private static MessageDataManager messageDataManager;
    private static volatile int totalNum = 0;

    public static synchronized MessageDataManager newInstance() {
        if (messageDataManager == null) {
            messageDataManager = new MessageDataManager();
        }
        return messageDataManager;
    }
    /**
     * 收消息处理
     *
     * @param
     */
    public void receive(Map<String, Object> stringsMap) {
//        String titleText = JSONObject.toJSONString(stringsMap);
//        JSONObject jsonObject = JSONObject.parseObject(titleText);
//        jsonObject.getObject()
        TfuAlarmTitle tfuAlarmTitle = new TfuAlarmTitle();
        tfuAlarmTitle.setTitleId(new Random().nextInt()+1234);
        tfuAlarmTitle.setVendorId((Integer)stringsMap.get("vendor_id"));
        tfuAlarmTitle.setTitle((String) stringsMap.get("title_text"));
//        tfuAlarmTitle.setInsertTime((String) stringsMap.get("insert_db_time"));
//        tfuAlarmTitle.setTimeStamp((Long)stringsMap.get("time_stamp"));
        DataCache.receiveMap.put(tfuAlarmTitle.getTitleId(), tfuAlarmTitle);
        totalNum++;
        if (totalNum > 5) {
            SpringContextUtils.getApplicationContext().publishEvent(new DataChangeEvent());
            totalNum = 0; //
        }
    }


}