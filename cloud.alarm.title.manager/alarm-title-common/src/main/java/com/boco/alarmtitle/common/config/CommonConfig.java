package com.boco.alarmtitle.common.config;

import com.boco.alarmtitle.common.dao.Impl.MessageDaoImpl;
import com.boco.alarmtitle.common.dao.JdbcTemplateImpl;
import com.boco.alarmtitle.common.dao.MessageDao;
import com.boco.component.datasource.druid.DruidDataSourceDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Description <p>数据库等配置</p>
 */
@Configuration
@EnableConfigurationProperties
public class CommonConfig {

    //private static Logger logger = LoggerFactory.getLogger(CommonConfig.class);
    @Autowired
    private DatabaseProperties databaseProperties;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        //logger.debug("数据库连接信息:{}", databaseProperties);
        DruidDataSourceDelegate dataSource = new DruidDataSourceDelegate();
        dataSource.setName(databaseProperties.getAlias());
        dataSource.setUrl(databaseProperties.getDriverUrl());
        dataSource.setDriverClassName(databaseProperties.getDriverClass());
        dataSource.setUsername(databaseProperties.getUser());
        dataSource.setPassword(databaseProperties.getPassword());
        dataSource.setInitialSize(1);
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(databaseProperties.getMinimumConnectionCount());
        dataSource.setMaxActive(databaseProperties.getMaximumActiveTime());
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery(databaseProperties.getHouseKeepingTestSql());
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        return dataSource;
    }

    @Bean(name = "jdbcTemplateImpl")
    public JdbcTemplateImpl jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplateImpl(dataSource);
    }

    @Bean(name = "messageDaoImpl")
    public MessageDao messageDaoImpl(@Qualifier("jdbcTemplateImpl") JdbcTemplateImpl jdbcTemplate) {
        MessageDaoImpl messageDao = new MessageDaoImpl();
        messageDao.setJdbcTemplate(jdbcTemplate);
        return messageDao;
    }
}
