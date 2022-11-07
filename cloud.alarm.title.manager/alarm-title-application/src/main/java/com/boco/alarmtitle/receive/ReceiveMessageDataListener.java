package com.boco.alarmtitle.receive;

import com.boco.alarmtitle.common.constant.AlarmFieldConstants;
import com.boco.alarmtitle.kafka.parse.KafkaMessageParser;
import com.boco.alarmtitle.kafka.MatcherKafkaConfig;
import com.boco.domain.DispMessageEntity;
import com.boco.kafka.message.MessageHeader;
import com.boco.kafka.message.MessageListener;
import com.boco.alarmtitle.service.MessageDataManager;
import com.boco.xdpp.bean.exports.proto.ProtoRealTimeMessage;
import com.boco.xdpp.bean.exports.proto.ProtoRealTimeMessage.ZipRealTimeMessage;
import com.boco.xdpp.model.alarm.exports.beans.AlarmCFP;
import com.boco.xdpp.model.alarm.exports.beans.AlarmFP;
import com.boco.xdpp.model.alarm.exports.beans.DynamicMessageMap;
import com.google.protobuf.ByteString;
import org.springframework.util.Assert;
import org.xdsp.smatcher.agent.manager.ClientAgent;
import org.xdsp.smatcher.agent.model.MatcherClientInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hao 2022/10/29 15:02
 */

public class ReceiveMessageDataListener implements MessageListener {

    private ClientAgent clientAgent;

    private final MessageDataManager messageDataManager;

    public ReceiveMessageDataListener(MatcherKafkaConfig matcherKafkaConfig) {
        this.clientAgent = new ClientAgent();
        MatcherClientInfo matcherClientInfo = buildMatcherInfo(matcherKafkaConfig);
        clientAgent.init(matcherClientInfo);
        try {
            clientAgent.onMessage(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.messageDataManager = MessageDataManager.newInstance();
    }

    @Override
    public void onMessage(MessageHeader header, byte[] data) {
        ByteString content = null;
        try {
            ZipRealTimeMessage zipRealTimeMessage = ZipRealTimeMessage.parseFrom(data);
            List<ProtoRealTimeMessage.ProtoRealTimeMessageEntry> protoRealTimeMessageEntrys = zipRealTimeMessage.getProtoRealTimeMessageEntryList();
            for (ProtoRealTimeMessage.ProtoRealTimeMessageEntry protoRealTimeMessageEntry : protoRealTimeMessageEntrys) {

                String entryMessageType = protoRealTimeMessageEntry.getEntryMessageType();
                content = protoRealTimeMessageEntry.getMessageContent();
                List<Integer> filterIdList = protoRealTimeMessageEntry.getFilterIdList();
                DispMessageEntity dispMessageEntity = new DispMessageEntity();
                dispMessageEntity.setHeader(header);
                dispMessageEntity.setProtoRealTimeMessageEntry(protoRealTimeMessageEntry);
                Map<String, Object> stringMap = null;
                if ("1407".equals(entryMessageType)) {
                    dispMessageEntity.setHeader(header);
                    dispMessageEntity.setProtoRealTimeMessageEntry(protoRealTimeMessageEntry);
                    DynamicMessageMap dynamicMessageMap = KafkaMessageParser.parseMessage1407(content);
                    AlarmFP alarmFP = dynamicMessageMap.getAlarmFP();
                    AlarmCFP alarmCFP = dynamicMessageMap.getAlarmCFP();
                    Object activeStatus = dynamicMessageMap.fieldOf(AlarmFieldConstants.ACTIVE_STATUS);
                    Object eventTime = dynamicMessageMap.fieldOf(AlarmFieldConstants.EVENT_TIME);
                    dispMessageEntity.setAlarmFP(alarmFP);
                    dispMessageEntity.setAlarmCFP(alarmCFP);
                    dispMessageEntity.setActiveStatus(activeStatus.toString());
                    dispMessageEntity.setEventTime(eventTime.toString());
                    //解析title_text信息
                    stringMap = dynamicMessageMap.getStringMap();
                    System.err.println(dynamicMessageMap.getStringMap());
                    System.err.println("----------------");
                    System.err.println(dynamicMessageMap.getIntMap());
                    System.err.println("----------------");

                }
                Assert.notEmpty(stringMap,()->{
                    throw new RuntimeException(AlarmFieldConstants.TITLE_TEXT_MAP_ERROR);
                });
                this.messageDataManager.receive(stringMap,dispMessageEntity);
            }
        } catch (Exception e) {
            String messageContent = "";
            if (content != null) {
                messageContent = new String(content.toByteArray());
            }
            System.out.println(messageContent);
//            logger.error(e.getMessage() + (StringUtils.isNotBlank(messageContent) ? ", xmlMsg = " + messageContent : messageContent), e);
        }
    }

    /**
     * 封装kafka一些配置信息
     *
     * @param matcherKafkaConfig
     * @return
     */
    private MatcherClientInfo buildMatcherInfo(MatcherKafkaConfig matcherKafkaConfig) {
        String zkAddress = matcherKafkaConfig.getZookeeperConnect();
        String zkPath = matcherKafkaConfig.getZookeeperPath();
        String topicName = matcherKafkaConfig.getTopicName();
        String topicZookeeperUrl = matcherKafkaConfig.getZookeeperConnect();

        MatcherClientInfo matcherClientInfo = new MatcherClientInfo();

        matcherClientInfo.setClientName(matcherKafkaConfig.getClientName());
        matcherClientInfo.setGroupId(matcherKafkaConfig.getGroupId());
        matcherClientInfo.setNeedSendClearAlarm(true);
        matcherClientInfo.setTopicZkAddress(topicZookeeperUrl);
        matcherClientInfo.setTopicName(topicName);
        matcherClientInfo.setZkAddress(zkAddress);
        matcherClientInfo.setZkPath(zkPath);
        matcherClientInfo.setUseDefaultTopic(false);
        matcherClientInfo.setReceiveThreadSize(matcherKafkaConfig.getReceiveThreadSize());
        Map<String, String> map = new HashMap<>();
        map.put("metadata.broker.list", matcherKafkaConfig.getBootstrapServers());
        matcherClientInfo.setParamMap(map);
        return matcherClientInfo;
    }
}
