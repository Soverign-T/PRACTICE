package com.boco.alarmtitle.kafka.parse;

import com.boco.xdpp.model.alarm.dynamic.*;
import com.boco.xdpp.model.alarm.dynamic.IDynamicMessage;
import com.boco.xdpp.model.alarm.dynamic.impl.AlarmDynamicMessageMeta;
import com.boco.xdpp.model.alarm.dynamic.impl.DynamicMessageFactory;
import com.boco.xdpp.model.alarm.exports.beans.DynamicMessageMap;
import com.google.protobuf.ByteString;
import com.google.protobuf.DynamicMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMessageParser {
//    private static Logger logger = LoggerFactory.getLogger(KafkaMessageParser.class);

    public static DynamicMessageMap parseMessage1407(ByteString srcAlarmBytes) {
        DynamicMessageMap dynamicMessageMap = null;
        try {
            long metaID = 0l;
            IDynamicMessage<AlarmDynamicMessageMeta, DynamicMessage> alarmDynamicMessage = DynamicMessageConstant.factory.buildMessage(metaID, srcAlarmBytes.toByteArray());
            DynamicMessage dynamicMessage = alarmDynamicMessage.getData();
            dynamicMessageMap = new DynamicMessageMap(dynamicMessage);
        } catch (Exception e) {
            e.printStackTrace();
//            logger.error("parseMessage1407 err.err={}", e);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return dynamicMessageMap;
    }
}