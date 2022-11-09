package com.boco.alarmtitle.common.dao;

import com.boco.domain.PmmpErrorResult;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.Table;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hao 2022/10/31 16:51
 */

public class JdbcTemplateImpl extends NamedParameterJdbcTemplate {
    private PlatformTransactionManager platformTransactionManager;
    private DefaultTransactionDefinition transactionDefinition;

    private ThreadLocal<TransactionStatus> transcationStatus = new ThreadLocal<TransactionStatus>();

    private String dbType = null;

    public JdbcTemplateImpl(DataSource dataSource) {
        super(dataSource);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String dbType = connection.getMetaData().getDatabaseProductName();
            this.dbType = dbType;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        platformTransactionManager = new DataSourceTransactionManager(dataSource);

    }
    public void beginTranstaion() {
        TransactionStatus tmp = platformTransactionManager.getTransaction(transactionDefinition);
        transcationStatus.set(tmp);
    }

    public void commit() {

        TransactionStatus tmp = transcationStatus.get();
        if (tmp == null) {
            throw new RuntimeException("no transcation");
        }
        platformTransactionManager.commit(tmp);
        transcationStatus.remove();
    }

    public void rollback() {

        TransactionStatus tmp = transcationStatus.get();
        if (tmp == null) {
            throw new RuntimeException("no transcation");
        }
        platformTransactionManager.rollback(tmp);
        transcationStatus.remove();

    }

    /**
     * 批量插入方法
     *
     * @param sqls   批量的sql
     * @param params sql索引对应的参数
     * @return 如果返回null说明批量更新失败，非null表示成功，结果集数组代表每行的影响数
     * @throws SQLException
     */
    public int[] batchUpdate(List<String> sqls, List<Map<String, Object>> params) {


        if (sqls == null || params == null) {
            throw new RuntimeException("sqls  or parms  is null");
        }


        if (sqls.size() != params.size()) {
            throw new RuntimeException("sqls size and parms size is not equal");
        }

        int[] result = new int[sqls.size()];

        try {

            this.beginTranstaion();

            //设置下日期的格式
            if (dbType.contains("Oracle")) {

                this.getJdbcOperations().execute("alter session set NLS_DATE_FORMAT='yyyy-mm-dd hh24:mi:ss'");
            }
            for (int rowIndex = 0; rowIndex < sqls.size(); rowIndex++) {
                result[rowIndex] = -1;
                String sql = sqls.get(rowIndex);

                Map<String, Object> paramMap = params.get(rowIndex);
                result[rowIndex] = this.update(sql, paramMap);
            }

            this.commit();
            return result;
        } catch (Exception e) {

            e.printStackTrace();
            this.rollback();
        }

        return null;

    }

    public int updateEx(List<String> sqls, List<Map<String, Object>> parms) {

//        List<String> sqls = new ArrayList<>();
//        sqls.add(sql);
        int[] resultArr = this.batchUpdate(sqls, parms);
        int result = resultArr != null ? resultArr[0] : -1;
        return result;

    }
}
