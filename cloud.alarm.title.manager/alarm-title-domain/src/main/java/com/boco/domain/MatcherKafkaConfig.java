package com.boco.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hao 2022/10/29 15:08
 */
public class MatcherKafkaConfig {
    private static final Logger logger = LoggerFactory.getLogger(MatcherKafkaConfig.class);


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

    public static Logger getLogger() {
        return logger;
    }

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

    //    /**
//     * 解析filterid
//     * @return
//     */
//    public List<Integer> getFilterList() {
//        List<Integer> filterList = new ArrayList<Integer>();
//        if(this.filter != null && this.filter.length() > 0) {
//            String[] filterIds = filter.split(",");
//            for(String fid: filterIds) {
//                try {
//                    int filterId = Integer.parseInt(fid);
//                    filterList.add(filterId);
//                } catch(Exception e) {
//                    logger.error("解析过滤器列表错误:filter_id={}", fid);
//                }
//            }
//        }
//        logger.info("设置过滤器列表:{},传入过滤器参数:{}", filterList, filter);
//        return filterList;
//    }
}