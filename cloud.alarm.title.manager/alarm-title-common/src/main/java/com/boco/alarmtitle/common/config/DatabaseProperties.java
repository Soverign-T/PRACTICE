package com.boco.alarmtitle.common.config;

import com.boco.gutil.registry.client.util.ConfigurationHelper;
import com.boco.ucmp.client.Configuration;
import lombok.ToString;

import java.util.Properties;

@ToString
public class DatabaseProperties {
	
	private String driverClass;
	
	private String driverUrl;
	
	private String user;
	
	private String password;
	
	private String alias;
	
	private String houseKeepingTestSql;
	
	private String testBeforeUse;
	
	private String trace;
	
	private int prototypeCount;
	
	private int maximumActiveTime;
	
	private int maximumConnectionCount;
	
	private int minimumConnectionCount;
	
	private int simultaneousBuildThrottle;
	
	private int houseKeepingSleepTime;

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDriverUrl() {
		return driverUrl;
	}

	public void setDriverUrl(String driverUrl) {
		this.driverUrl = driverUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getHouseKeepingTestSql() {
		return houseKeepingTestSql;
	}

	public void setHouseKeepingTestSql(String houseKeepingTestSql) {
		this.houseKeepingTestSql = houseKeepingTestSql;
	}

	public String getTestBeforeUse() {
		return testBeforeUse;
	}

	public void setTestBeforeUse(String testBeforeUse) {
		this.testBeforeUse = testBeforeUse;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public int getPrototypeCount() {
		return prototypeCount;
	}

	public void setPrototypeCount(int prototypeCount) {
		this.prototypeCount = prototypeCount;
	}

	public int getMaximumActiveTime() {
		return maximumActiveTime;
	}

	public void setMaximumActiveTime(int maximumActiveTime) {
		this.maximumActiveTime = maximumActiveTime;
	}

	public int getMaximumConnectionCount() {
		return maximumConnectionCount;
	}

	public void setMaximumConnectionCount(int maximumConnectionCount) {
		this.maximumConnectionCount = maximumConnectionCount;
	}

	public int getMinimumConnectionCount() {
		return minimumConnectionCount;
	}

	public void setMinimumConnectionCount(int minimumConnectionCount) {
		this.minimumConnectionCount = minimumConnectionCount;
	}

	public int getSimultaneousBuildThrottle() {
		return simultaneousBuildThrottle;
	}

	public void setSimultaneousBuildThrottle(int simultaneousBuildThrottle) {
		this.simultaneousBuildThrottle = simultaneousBuildThrottle;
	}

	public int getHouseKeepingSleepTime() {
		return houseKeepingSleepTime;
	}

	public void setHouseKeepingSleepTime(int houseKeepingSleepTime) {
		this.houseKeepingSleepTime = houseKeepingSleepTime;
	}

	public DatabaseProperties init(DatabaseProperties databaseProperties) throws Exception{
		Configuration configuration = ConfigurationHelper.getUcmpConf();
		String dbType = configuration.getString("dbType", "oracle");
		//String dbType = database.getProperty("database");
		Properties properties = null;
		if ("mysql".equals(dbType)){
			properties = configuration.getProperties("database-mysql");
		}else{
			properties = configuration.getProperties("database-oracle");
		}
		String hibernateDialect = properties.getProperty("hibernate.dialect");
		String jdbc3ProxoolAlias = properties.getProperty("jdbc-3.proxool.alias");
		databaseProperties.setAlias(jdbc3ProxoolAlias);
		String jdbc3ProxoolDriverClass = properties.getProperty("jdbc-3.proxool.driver-class");
		databaseProperties.setDriverClass(jdbc3ProxoolDriverClass);
		String jdbc3ProxoolDriverUrl = properties.getProperty("jdbc-3.proxool.driver-url");
		databaseProperties.setDriverUrl(jdbc3ProxoolDriverUrl);
		String jdbc3ProxoolHouseKeepingSleepTime = properties.getProperty("jdbc-3.proxool.house-keeping-sleep-time");
		databaseProperties.setHouseKeepingSleepTime(Integer.parseInt(jdbc3ProxoolHouseKeepingSleepTime));
		String jdbc3ProxoolHouseKeepingTestSql = properties.getProperty("jdbc-3.proxool.house-keeping-test-sql");
		databaseProperties.setHouseKeepingTestSql(jdbc3ProxoolHouseKeepingTestSql);
		String jdbc3ProxoolMaximumActiveTime = properties.getProperty("jdbc-3.proxool.maximum-active-time");
		databaseProperties.setMaximumActiveTime(Integer.parseInt(jdbc3ProxoolMaximumActiveTime));
		String jdbc3ProxoolMaximumConnectionCount = properties.getProperty("jdbc-3.proxool.maximum-connection-count");
		databaseProperties.setMaximumConnectionCount(Integer.parseInt(jdbc3ProxoolMaximumConnectionCount));
		String jdbc3ProxoolPassword = properties.getProperty("jdbc-3.proxool.password");
		databaseProperties.setPassword(jdbc3ProxoolPassword);
		String jdbc3ProxoolPrototypeCount = properties.getProperty("jdbc-3.proxool.prototype-count");
		databaseProperties.setPrototypeCount(Integer.parseInt(jdbc3ProxoolPrototypeCount));
		String jdbc3ProxoolSimultaneousBuildThrottle = properties.getProperty("jdbc-3.proxool.simultaneous-build-throttle");
		databaseProperties.setSimultaneousBuildThrottle(Integer.parseInt(jdbc3ProxoolSimultaneousBuildThrottle));
		String jdbc3ProxoolTestBeforeUse = properties.getProperty("jdbc-3.proxool.test-before-use");
		databaseProperties.setTestBeforeUse(jdbc3ProxoolTestBeforeUse);
		String jdbc3ProxoolTrace = properties.getProperty("jdbc-3.proxool.trace");
		databaseProperties.setTrace(jdbc3ProxoolTrace);
		String jdbc3ProxoolUser = properties.getProperty("jdbc-3.proxool.user");
		databaseProperties.setUser(jdbc3ProxoolUser);
		return databaseProperties;
	}
}
