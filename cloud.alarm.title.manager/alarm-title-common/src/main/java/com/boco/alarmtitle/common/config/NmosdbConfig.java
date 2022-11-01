package com.boco.alarmtitle.common.config;

import com.boco.gutil.registry.client.util.ConfigurationHelper;
import com.boco.ucmp.client.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @author hao 2022/10/31 15:38
 */
public class NmosdbConfig {
    private static Logger logger = LoggerFactory.getLogger(NmosdbConfig.class);
    public static String dbUser;
    public static String dbPassword;
    public static String dbDriver;
    public static String dbUrl;
    public static String dbType = "oracle";
    public static int maxConnCount = 100;
    public static int prototypeCount = 5;
    public static int queryTimeOutSeconds = 5;

    public static void initNmosdb() throws IOException {
        Configuration configuration = ConfigurationHelper.getUcmpConf();
        Properties nmosdbProperties = configuration.getProperties("database-oracle");
        String url = nmosdbProperties.getProperty("jdbc-3.proxool.driver-url");
        String driver = nmosdbProperties.getProperty("jdbc-3.proxool.driver-class");
        String user = nmosdbProperties.getProperty("jdbc-3.proxool.user");
        String password = nmosdbProperties.getProperty("jdbc-3.proxool.password");
        String prototypeCount = nmosdbProperties.getProperty("jdbc-3.proxool.prototype-count");
        String maxConnCount = nmosdbProperties.getProperty("jdbc-3.proxool.maximum-connection-count");
        String queryTimeOutSeconds = nmosdbProperties.getProperty("jdbc-3.proxool.maximum-query-timeout-seconds");
        NmosdbConfig.dbUrl = url;
        NmosdbConfig.dbDriver = driver;
        NmosdbConfig.dbUser = user;
        NmosdbConfig.dbPassword = password;
        try {
            if (prototypeCount != null && !prototypeCount.isEmpty()){
                NmosdbConfig.prototypeCount = Integer.parseInt(prototypeCount);
            }
        }catch (Exception e){
            logger.error("jdbc-1.proxool.prototype-count is err number");
        }
        try {
            if (maxConnCount != null && !maxConnCount.isEmpty()){
                NmosdbConfig.maxConnCount = Integer.parseInt(maxConnCount);
            }
        }catch (Exception e){
            logger.error("jdbc-1.proxool.maximum-connection-count is err number");
        }

        try {
            if (queryTimeOutSeconds != null && !"".equals(queryTimeOutSeconds)){
                NmosdbConfig.queryTimeOutSeconds = Integer.parseInt(queryTimeOutSeconds);
            }
        }catch (Exception e){
            logger.error("jdbc-1.proxool.maximum-query-timeout-seconds is err number");
        }
    }
}

