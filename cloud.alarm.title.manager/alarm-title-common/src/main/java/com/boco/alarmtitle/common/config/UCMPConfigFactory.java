package com.boco.alarmtitle.common.config;

import com.boco.gutil.registry.client.util.ConfigurationHelper;

public class UCMPConfigFactory {
    public static void initConfig() throws Exception{
        String appDomain = System.getProperty("app_domain");
        if (appDomain == null || appDomain.length() == 0) {
            throw new Exception("please set system properties 'app_domain',eg:-Dapp_domain=instances");
        }
        String appVersion = System.getProperty("app_version");
        if (appVersion == null || appVersion.length() == 0) {
            throw new Exception("please set system properties 'app_version',eg:-Dapp_version=2.0.0");
        }
        String appName = System.getProperty("app_name");
        if (appName == null || appName.length() == 0) {
            throw new Exception("please set system properties 'app_name',eg:-DappName=cloud.alarm.view");
        }
        String instanceName = System.getProperty("app_instance");
        if (instanceName == null || instanceName.length() == 0) {
            throw new Exception("please set system properties 'app_instance',eg:-Dapp_instance=Instance-1");
        }
        String zookeeperUrl = System.getProperty("config.zookeeper.url");
        if (zookeeperUrl == null || zookeeperUrl.length() == 0) {
            throw new Exception("please set system properties 'config.zookeeper.url',eg:-Dconfig.zookeeper.url=10.10.1.1:2181,10.10.1.2:2181");
        }
        ConfigurationHelper configurationHelper = ConfigurationHelper.getInstance();
        configurationHelper.init(appDomain, appName, instanceName, appVersion, zookeeperUrl);
    }
}
