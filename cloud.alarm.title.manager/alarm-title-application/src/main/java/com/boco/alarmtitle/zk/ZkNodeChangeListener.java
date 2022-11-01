package com.boco.alarmtitle.zk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boco.alarmtitle.nodes.ProcessIpPortVo;
import com.boco.gutil.registry.client.util.ConfigurationHelper;
import com.boco.ucmp.client.Configuration;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ZkNodeChangeListener {

    private Logger logger = LoggerFactory.getLogger(ZkNodeChangeListener.class);
    public final Map<String, ProcessIpPortVo> processNodeMap = new HashMap<>();
    public final TreeMap<Integer, String> processUseMap = new TreeMap<>();
    public String SUB_ZK_PATH = "/sub_process_client_path";
    public String ZkClusterPath;
    private ZkClient zkClient;

    public void init(String subZkPath) throws Exception{
        this.SUB_ZK_PATH = subZkPath;
        Configuration configuration = ConfigurationHelper.getUcmpConf();
        Properties properties = configuration.getProperties("/public/zookeeper/ucmp");
        String zkClusterPath = properties.getProperty("zookeeper.url");
        ZkClusterPath = zkClusterPath;
        zkClient = new ZkClient(zkClusterPath);
        boolean exists = zkClient.exists(SUB_ZK_PATH);
        if (!exists){
            zkClient.create(SUB_ZK_PATH, "", CreateMode.PERSISTENT);
        }
        List<String> childNodeList = zkClient.getChildren(SUB_ZK_PATH);
        for (String zkPath : childNodeList) {
            boolean ifExist = processNodeMap.containsKey(zkPath);
            if (!ifExist){
                String ipPort = zkClient.readData(SUB_ZK_PATH + "/" + zkPath);
                ProcessIpPortVo processIpPortVo = parseIpPortData(ipPort);
                processNodeMap.put(zkPath, processIpPortVo);
                processUseMap.put(processIpPortVo.getUseTimes(), zkPath);
            }
        }

        watchServer();
    }

    public void watchServer() {
        //监听特定节点的子节点的变化
        zkClient.subscribeChildChanges(SUB_ZK_PATH, new IZkChildListener() {
            /**
             * 当删除或者添加一个子节点以及删除父节点本身,会触发此事件
             * @param parentNode :代表发生变化的节点的父节点的名称
             * @param childNodeList：发生变化后父节点下面所有子节点名称
             * @throws Exception
             */
            public void handleChildChange(String parentNode, List<String> childNodeList)
                    throws Exception {
                logger.info(parentNode+"---->"+childNodeList);
                if (childNodeList != null){
                    processNodeMap.clear();
                    zkClient.unsubscribeAll();
                    watchServer();
                    for (String zkPath : childNodeList) {
                        String ipPort = zkClient.readData(SUB_ZK_PATH + "/" + zkPath);
                        ProcessIpPortVo processIpPortVo = parseIpPortData(ipPort);
                        processNodeMap.put(zkPath, processIpPortVo);
                        processUseMap.put(0, zkPath);
                        watchData(SUB_ZK_PATH + "/" + zkPath);
//                        if (!processNodeMap.containsKey(zkPath)){
//                        }
                    }
                }
            }
        });
    }

    public void watchData(String path){
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                logger.info("Start SubscribeDataChanges.path" + path);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

            }
        });
    }

    public void unWatchData(String path){
        zkClient.unsubscribeDataChanges(SUB_ZK_PATH, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                logger.warn("UnsubscribeDataChanges.path=" + path);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

            }
        });
    }

    public ProcessIpPortVo getProcessInstance(){
        Iterator<Map.Entry<Integer, String>> iterator = processUseMap.entrySet().iterator();
        if (!iterator.hasNext()){
            return null;
        }
        Map.Entry<Integer, String> next = iterator.next();
        Integer useTimes = next.getKey();
        String zkPath = processUseMap.entrySet().iterator().next().getValue();
        processUseMap.remove(useTimes);
        processUseMap.put(useTimes + 1, zkPath);
        return processNodeMap.get(zkPath);
    }

    public ProcessIpPortVo getProcessInstanceBySubGroup(String subGroup) {
        if(StringUtils.isBlank(subGroup)){
            return null;
        }

        Iterator<Map.Entry<String, ProcessIpPortVo>> iterator = processNodeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ProcessIpPortVo> map = iterator.next();
//            String zkPath = map.getKey();
            ProcessIpPortVo processIpPortVo = map.getValue();
            String subGroupString = processIpPortVo.getSubGroup();
            List<String> list = Arrays.asList(subGroupString.split(","));
//            logger.info("getProcessInstanceBySubGroup@@@:processIpPortVo=" + processIpPortVo.toString() + ",list=" + list.get(0) + ",processUseMapSize=" + processUseMap.size() + ",processNodeMapSize=" + processNodeMap.size());
            if (list.contains(subGroup)) {
                return processIpPortVo;
            }
        }
        return null;
    }

    public boolean ifNodeExist(ProcessIpPortVo node){
        Collection<ProcessIpPortVo> values = processNodeMap.values();
        for (ProcessIpPortVo processIpPortVo : values) {
            if (processIpPortVo.equals(node)){
                return true;
            }
        }
        return false;
    }

    private ProcessIpPortVo parseIpPortData(String ipPort){
        ProcessIpPortVo processIpPortVo = JSONObject.parseObject(ipPort, ProcessIpPortVo.class);
//        String[] split = ipPort.split(":");
//        ProcessIpPortVo processIpPortVo = new ProcessIpPortVo();
//        processIpPortVo.setIp(split[0]);
//        processIpPortVo.setPort(split[1]);
        return processIpPortVo;
    }

    public synchronized int getNodesSize(){
        return processNodeMap.size();
    }

    public synchronized void addLoadTimes(String ipPort){
        Map<Integer, String> tempMap = new HashMap<>();
        tempMap.putAll(processUseMap);
        Iterator<Map.Entry<Integer, String>> iterator = tempMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, String> next = iterator.next();
            String zkPath = next.getValue();
            ProcessIpPortVo processIpPortVo = processNodeMap.get(zkPath);
            String ipPortVo = processIpPortVo.getIpPort();
            if (ipPortVo.equals(ipPort)){
                Integer key = next.getKey();
                processUseMap.remove(key);
                key += 1;
                processUseMap.put(key, zkPath);
                processIpPortVo.setUseTimes(key);
                writeZkNodeData(processIpPortVo);
            }
        }
    }

    private void writeZkNodeData(ProcessIpPortVo client){
        String appDomain = System.getProperty("app_instance");
        String path = SUB_ZK_PATH + "/" + appDomain;
        zkClient.writeData(path, JSON.toJSONString(client));
    }
}
