package com.boco.alarmtitle;


import com.boco.alarmtitle.common.config.ApplicationConfig;
import com.boco.alarmtitle.common.config.DatabaseProperties;
import com.boco.alarmtitle.common.config.DatabaseConfiguration;
import com.boco.alarmtitle.common.config.UCMPConfigFactory;
import com.boco.alarmtitle.common.util.SpringContextUtils;
import com.boco.alarmtitle.kafka.parse.AlarmDescLoader;
import com.boco.alarmtitle.receive.ReceiveMessageDataListener;
import com.boco.alarmtitle.zk.ZKRegisterService;
import com.boco.alarmtitle.zk.ZkNodeChangeListener;
import com.boco.alarmtitle.kafka.MatcherKafkaConfig;
import com.boco.gutil.registry.client.util.ConfigurationHelper;
import com.boco.ucmp.client.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.util.Properties;

/**
 * @author hao 2022/10/29 11:36
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class Application {

    public static void main(String[] args) throws Exception {
        System.setProperty("zookeeper.sasl.client", Boolean.FALSE.toString());
        UCMPConfigFactory.initConfig();
        ZkNodeChangeListener zkNodeChangeListener = initSystemConfig();
        Configuration configuration = ConfigurationHelper.getUcmpConf();
        Properties application = configuration.getProperties("application");
        String serverPort = application.getProperty("server.port");
        if (serverPort == null || serverPort.isEmpty()) {
            serverPort = "9527";
        }
        ApplicationConfig.serverPort = serverPort;
        System.setProperty("server.port", serverPort);
        DatabaseConfiguration.initNmosdb();
        //初始化PB
        AlarmDescLoader.loadAlarmObjectMapping();
        SpringApplication.run(Application.class, args);
        ZKRegisterService zkRegisterService = ZKRegisterService.newInstance();
        zkRegisterService.setZkNodeChangeListener(zkNodeChangeListener);
        MatcherKafkaConfig matcherKafkaConfig = receiveTopic();
        new ReceiveMessageDataListener(matcherKafkaConfig);
    }

    @Bean
    public SpringContextUtils initApplicationContext() {
        return new SpringContextUtils();
    }

    @Bean
    public DatabaseProperties initDatabaseProperties() throws Exception {
        DatabaseProperties databaseProperties = new DatabaseProperties();
        return databaseProperties.init(databaseProperties);
    }
    @Bean
    public static ZkNodeChangeListener initSystemConfig() throws Exception {
        Configuration configuration = ConfigurationHelper.getUcmpConf();
        Properties properties = configuration.getProperties("system");
        String subProcessZkPath = properties.getProperty("sub.process.zk.path");
        ZKRegisterService.SUB_ZK_PATH = subProcessZkPath;
        ZkNodeChangeListener zkNodeChangeListener = new ZkNodeChangeListener();
        zkNodeChangeListener.init(subProcessZkPath);
        return zkNodeChangeListener;
    }

    private static MatcherKafkaConfig receiveTopic() throws IOException {
        Configuration configuration = ConfigurationHelper.getUcmpConf();
        Properties properties = configuration.getProperties("inputTopic");
        String groupId = properties.getProperty("group.id");
        String topicName = properties.getProperty("topic.name");
        String zookeeperConnect = properties.getProperty("zookeeper.connect");
        String zookeeperPath = properties.getProperty("zookeeper.path");
        String clientName = properties.getProperty("client.name");
        String kafkaBrokers = properties.getProperty("kafka.brokers");
        String receiveThreadSize = properties.getProperty("receive.thread.size");

        MatcherKafkaConfig config = new MatcherKafkaConfig();
        config.setGroupId(groupId);
        config.setTopicName(topicName);
        config.setZookeeperConnect(zookeeperConnect);
        config.setZookeeperPath(zookeeperPath);
        config.setClientName(clientName);
        config.setBootstrapServers(kafkaBrokers);
        if (receiveThreadSize != null && !receiveThreadSize.isEmpty()){
            config.setReceiveThreadSize(1);
        }
        return config;
    }

}
