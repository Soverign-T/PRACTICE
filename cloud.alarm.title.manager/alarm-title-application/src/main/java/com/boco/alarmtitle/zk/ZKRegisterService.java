package com.boco.alarmtitle.zk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boco.alarmtitle.common.config.ApplicationConfig;
import com.boco.alarmtitle.nodes.ProcessIpPortVo;
import com.boco.gutil.registry.client.util.ConfigurationHelper;
import com.boco.ucmp.client.Configuration;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ZKRegisterService {

    private Logger logger = LoggerFactory.getLogger(ZKRegisterService.class);
    public static String SUB_ZK_PATH = "/sub_process_client_path";
    public static String ZkClusterPath;
    private ZkClient zkClient;
    private String appDomain;
    private Integer procNodes;
    private static ZKRegisterService zkRegisterService;

    private ZkNodeChangeListener zkNodeChangeListener;

    private ZKRegisterService(){}

    public static synchronized ZKRegisterService newInstance() throws Exception {
        if (zkRegisterService == null){
            zkRegisterService = new ZKRegisterService();
        }
        zkRegisterService.init();
        return zkRegisterService;
    }

    public void init() throws Exception{
        appDomain = System.getProperty("app_instance");
        Configuration configuration = ConfigurationHelper.getUcmpConf();
        Properties properties = configuration.getProperties("/public/zookeeper/ucmp");
        String zkClusterPath = properties.getProperty("zookeeper.url");
        ZkClusterPath = zkClusterPath;
        zkClient = new ZkClient(zkClusterPath);

        Properties systemProperties = configuration.getProperties("system");
        String procNodesStr = systemProperties.getProperty("proc.nodes");
        procNodes = Integer.parseInt(procNodesStr);
    }

    public void registerProcess(ProcessIpPortVo client){
        String path = SUB_ZK_PATH + "/" + appDomain;
        String result = zkClient.create(path, JSON.toJSONString(client), CreateMode.EPHEMERAL);
        logger.info("CreateProcess result=" + result);
    }

    public Integer getStopProcessIndex(String subGroup) throws UnknownHostException {
        String path = SUB_ZK_PATH + "/" + appDomain;
        if (zkClient.exists(path)){
            try {
                zkClient.delete(path);
            }catch (Exception e){
                logger.warn("ZKNode [{}] is auto delete", path);
            }
        }
        List<String> childNodeList = zkClient.getChildren(SUB_ZK_PATH);
        Map<Integer, Integer> map = new HashMap<>();
        for (String zkPath : childNodeList) {
            String ipPort = zkClient.readData(SUB_ZK_PATH + "/" + zkPath);
            ProcessIpPortVo processIpPortVo = parseIpPortData(ipPort);
            Integer index = processIpPortVo.getIndex();
            map.put(index, index);
        }
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        ProcessIpPortVo processIpPortVo = new ProcessIpPortVo();
        processIpPortVo.setIp(ip);
        processIpPortVo.setPort(ApplicationConfig.serverPort);
        processIpPortVo.setSubGroup(subGroup);
        for (int i = 0; i < procNodes; i++){
            Integer integer = map.get(i);
            if (integer == null){
                processIpPortVo.setIndex(i);
                registerProcess(processIpPortVo);
                return i;
            }
        }
        processIpPortVo.setIndex(zkNodeChangeListener.getNodesSize());
        registerProcess(processIpPortVo);
        return -1;
    }

    private ProcessIpPortVo parseIpPortData(String ipPort){
        ProcessIpPortVo processIpPortVo = JSONObject.parseObject(ipPort, ProcessIpPortVo.class);
        return processIpPortVo;
    }

    public ZkNodeChangeListener getZkNodeChangeListener() {
        return zkNodeChangeListener;
    }

    public void setZkNodeChangeListener(ZkNodeChangeListener zkNodeChangeListener) {
        this.zkNodeChangeListener = zkNodeChangeListener;
    }
}
