package com.boco.alarmtitle.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hao 2022/10/29 15:08
 */
public class MatcherKafkaConfig {

    private String subScription;
    private String moduleIdList;
    private String msgTypeList;
    private String clientName;
    private String topicName;
    private String groupId;
    private boolean needSendClearAlarm;
    private String zookeeperPath;
    private String zookeeperConnect;
    private boolean useDefaultTopic;
    private String bootstrapServers;
    private Integer receiveThreadSize = 3;

    private String filter;

    public String getSubScription() {
        return subScription;
    }

    public void setSubScription(String subScription) {
        this.subScription = subScription;
    }

    public String getModuleIdList() {
        return moduleIdList;
    }

    public void setModuleIdList(String moduleIdList) {
        this.moduleIdList = moduleIdList;
    }

    public String getMsgTypeList() {
        return msgTypeList;
    }

    public void setMsgTypeList(String msgTypeList) {
        this.msgTypeList = msgTypeList;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isNeedSendClearAlarm() {
        return needSendClearAlarm;
    }

    public void setNeedSendClearAlarm(boolean needSendClearAlarm) {
        this.needSendClearAlarm = needSendClearAlarm;
    }

    public String getZookeeperPath() {
        return zookeeperPath;
    }

    public void setZookeeperPath(String zookeeperPath) {
        this.zookeeperPath = zookeeperPath;
    }

    public String getZookeeperConnect() {
        return zookeeperConnect;
    }

    public void setZookeeperConnect(String zookeeperConnect) {
        this.zookeeperConnect = zookeeperConnect;
    }

    public boolean isUseDefaultTopic() {
        return useDefaultTopic;
    }

    public void setUseDefaultTopic(boolean useDefaultTopic) {
        this.useDefaultTopic = useDefaultTopic;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public Integer getReceiveThreadSize() {
        return receiveThreadSize;
    }

    public void setReceiveThreadSize(Integer receiveThreadSize) {
        this.receiveThreadSize = receiveThreadSize;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "MatcherKafkaConfig{" +
                "subScription='" + subScription + '\'' +
                ", moduleIdList='" + moduleIdList + '\'' +
                ", msgTypeList='" + msgTypeList + '\'' +
                ", clientName='" + clientName + '\'' +
                ", topicName='" + topicName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", needSendClearAlarm=" + needSendClearAlarm +
                ", zookeeperPath='" + zookeeperPath + '\'' +
                ", zookeeperConnect='" + zookeeperConnect + '\'' +
                ", useDefaultTopic=" + useDefaultTopic +
                ", bootstrapServers='" + bootstrapServers + '\'' +
                ", receiveThreadSize=" + receiveThreadSize +
                ", filter='" + filter + '\'' +
                '}';
    }
}