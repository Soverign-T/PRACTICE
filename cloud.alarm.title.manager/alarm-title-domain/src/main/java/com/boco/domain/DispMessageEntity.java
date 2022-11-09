package com.boco.domain;

import com.boco.kafka.message.MessageHeader;
import com.boco.xdpp.bean.exports.proto.ProtoRealTimeMessage;
import com.boco.xdpp.model.alarm.exports.beans.AlarmCFP;
import com.boco.xdpp.model.alarm.exports.beans.AlarmFP;
import lombok.ToString;

@ToString
public class DispMessageEntity {
    //private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private MessageHeader header;
    private ProtoRealTimeMessage.ProtoRealTimeMessageEntry protoRealTimeMessageEntry;
    private String eventTime;
    private AlarmFP alarmFP;
    private AlarmCFP alarmCFP;
    private String activeStatus;

    public MessageHeader getHeader() {
        return header;
    }

    public void setHeader(MessageHeader header) {
        this.header = header;
    }

    public ProtoRealTimeMessage.ProtoRealTimeMessageEntry getProtoRealTimeMessageEntry() {
        return protoRealTimeMessageEntry;
    }

    public void setProtoRealTimeMessageEntry(ProtoRealTimeMessage.ProtoRealTimeMessageEntry protoRealTimeMessageEntry) {
        this.protoRealTimeMessageEntry = protoRealTimeMessageEntry;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public AlarmFP getAlarmFP() {
        return alarmFP;
    }

    public void setAlarmFP(AlarmFP alarmFP) {
        this.alarmFP = alarmFP;
    }

    public AlarmCFP getAlarmCFP() {
        return alarmCFP;
    }

    public void setAlarmCFP(AlarmCFP alarmCFP) {
        this.alarmCFP = alarmCFP;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }


}
