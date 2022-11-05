package com.boco.alarmtitle.kafka.parse;

import com.boco.xdpp.bean.exports.proto.ProtoRealTimeMessage;
import com.boco.xdpp.model.alarm.dynamic.DynamicMessageConstant;
import com.boco.xdpp.model.alarm.dynamic.IDynamicMessage;
import com.boco.xdpp.model.alarm.dynamic.impl.AlarmDynamicMessageMeta;
import com.google.protobuf.ByteString;
import com.google.protobuf.DynamicMessage;
import org.apache.log4j.Logger;

import java.util.Map;

public class KafkaMessageBuilder {

    private static Logger logger = Logger.getLogger(KafkaMessageBuilder.class);

    private static DynamicMessage createDynamicMessage(Map<String, String> propertiesMap) {
        DynamicMessage dynamicMessage = null;
        try {
            IDynamicMessage<AlarmDynamicMessageMeta, DynamicMessage> dynamicMessageMap = buildAlarmMessage(propertiesMap);
            dynamicMessage = dynamicMessageMap.getData();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("createDynamicMessage error.", e);
        }
        return dynamicMessage;
    }

    private static IDynamicMessage<AlarmDynamicMessageMeta, DynamicMessage> buildAlarmMessage(
            Map<String, String> dynamicAttributes) {
        try {
            IDynamicMessage<AlarmDynamicMessageMeta, DynamicMessage> dynamicMessage;
            dynamicMessage = DynamicMessageConstant.factory.buildMessage(DynamicMessageConstant.LATEST_META,
                    dynamicAttributes.entrySet());
            return dynamicMessage;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("buildMessage error.", e);
        } catch (Throwable th) {
            th.printStackTrace();
            logger.error("buildMessage error.", th);
        }
        return null;
    }

    public static byte[] buildZipProto(Map<String, String> propertiesMap) {
        DynamicMessage dynamicMessage = createDynamicMessage(propertiesMap);
        ByteString proto = dynamicMessage.toByteString();
        ProtoRealTimeMessage.ZipPbAlarm.Builder builder = ProtoRealTimeMessage.ZipPbAlarm.newBuilder();
        builder.addPbAlarm(proto);
        byte[] zip = builder.build().toByteArray();
        return zip;
    }
}
