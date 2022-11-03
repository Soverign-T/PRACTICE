package com.boco.alarmtitle.common.dao.Impl;

import com.boco.alarmtitle.common.dao.JdbcTemplateImpl;
import com.boco.alarmtitle.common.dao.MessageDao;
import net.sourceforge.jtds.jdbc.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hao 2022/11/1 14:17
 */
public class MessageDaoImpl implements MessageDao {

    private JdbcTemplateImpl jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplateImpl jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplateImpl getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public List<String> selectAll() {
        String SQL = "select ne_id from nmosdb.pmmp_error_result";
        HashMap<String, Object> params = new HashMap<>();
        return jdbcTemplate.queryForList(SQL, params, String.class);

    }

    @Override
    public Integer insertBatch() {
        String sql = "INSERT INTO nmosdb.PMMP_ERROR_RESULT (topo_id,ne_id,ne_name,PORT_ID,PORT_NAME,PORT_TYPE,BANDWIDTH,TODAY_TIME,TODAY_IN_RATE,TODAY_OUT_RATE,YESTERDAY_TIME,YESTERDAY_IN_RATE,YESTERDAY_OUT_RATE,IN_EFFICIENCY,OUT_EFFICIENCY,IN_TONGBI,OUT_TONGBI,ALARM_REASON) VALUES \n" +
                "(1,1,1,1,1,1,1,sysdate,1,1,sysdate-1,1,1,1,1,1,1,1)";
        HashMap<String, Object> map = new HashMap<>();
//        map.put("topo_id", "1");
//        map.put("ne_id", 1);
//        map.put("ne_name", "1");
//        map.put("port_id", 1);
//        map.put("port_name", "1");
//        map.put("PORT_TYPE", 1);
//        map.put("BANDWIDTH", 1);
//        map.put("TODAY_TIME", null);
//        map.put("TODAY_IN_RATE", 1);
//        map.put("TODAY_OUT_RATE", 1);
//        map.put("YESTERDAY_TIME", null);
//        map.put("YESTERDAY_IN_RATE", 1);
//        map.put("YESTERDAY_OUT_RATE", 1);
//        map.put("IN_EFFICIENCY", 1);
//        map.put("OUT_EFFICIENCY", 1);
//        map.put("IN_TONGBI", 1);
//        map.put("OUT_TONGBI", 1);
//        map.put("ALARM_REASON", "1");
        return jdbcTemplate.updateEx(sql, map);
    }

    public static void main(String[] args) {
        MessageDaoImpl messageDao = new MessageDaoImpl();
        Integer integer = messageDao.insertBatch();
        System.err.println(integer);
    }

}
