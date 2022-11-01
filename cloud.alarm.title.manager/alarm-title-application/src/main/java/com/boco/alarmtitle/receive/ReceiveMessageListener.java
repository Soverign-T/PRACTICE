package com.boco.alarmtitle.receive;

import com.boco.domain.DispMessageEntity;
import com.boco.domain.MatcherKafkaConfig;
import com.boco.kafka.message.MessageHeader;
import com.boco.kafka.message.MessageListener;
import com.boco.alarmtitle.client.SubClientManager;
import com.boco.xdpp.bean.exports.proto.ProtoRealTimeMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import org.xdsp.smatcher.agent.manager.ClientAgent;
import org.xdsp.smatcher.agent.model.MatcherClientInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hao 2022/10/29 15:02
 */

public class ReceiveMessageListener implements MessageListener {

    private ClientAgent clientAgent;

    private final SubClientManager subClientManager;

    public ReceiveMessageListener(MatcherKafkaConfig matcherKafkaConfig) {
        this.clientAgent = new ClientAgent();
        MatcherClientInfo matcherClientInfo = buildMatcherInfo(matcherKafkaConfig);
        clientAgent.init(matcherClientInfo);
        try {
            clientAgent.onMessage(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.subClientManager = SubClientManager.newInstance();
    }

    @Override
    public void onMessage(MessageHeader header, byte[] data) {
        ProtoRealTimeMessage.ZipRealTimeMessage zipRealTimeMessage = null;
        try {
            zipRealTimeMessage = ProtoRealTimeMessage.ZipRealTimeMessage.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
        List<ProtoRealTimeMessage.ProtoRealTimeMessageEntry> protoRealTimeMessageEntrys = zipRealTimeMessage.getProtoRealTimeMessageEntryList();
        for (ProtoRealTimeMessage.ProtoRealTimeMessageEntry protoRealTimeMessageEntry : protoRealTimeMessageEntrys) {
            DispMessageEntity dispMessageEntity = new DispMessageEntity();
            dispMessageEntity.setHeader(header);
            dispMessageEntity.setProtoRealTimeMessageEntry(protoRealTimeMessageEntry);

                /*AlarmFP alarmFP = dynamicMessageMap.getAlarmFP();
                AlarmCFP alarmCFP = dynamicMessageMap.getAlarmCFP();
                Object activeStatus = dynamicMessageMap.fieldOf(AlarmFieldConstants.ACTIVE_STATUS);
                Object eventTime = dynamicMessageMap.fieldOf(AlarmFieldConstants.EVENT_TIME);
                dispMessageEntity.setAlarmFP(alarmFP);
                dispMessageEntity.setAlarmCFP(alarmCFP);
                dispMessageEntity.setActiveStatus(activeStatus.toString());
                dispMessageEntity.setEventTime(eventTime.toString());*/

            this.subClientManager.receive(dispMessageEntity);
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
