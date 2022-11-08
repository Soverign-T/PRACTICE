package com.boco.alarmtitle.common.dao.Impl;

import com.boco.alarmtitle.common.dao.JdbcTemplateImpl;
import com.boco.alarmtitle.common.dao.MessageDataDao;
import com.boco.domain.PmmpErrorResult;
import com.boco.domain.TfuAlarmTitle;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hao 2022/11/1 14:17
 */
//接收数据的字段和数据库字段是不一致的,但是经过map的转换修改最终实体类和数据库字段对应上了
public class MessageDataDaoImpl implements MessageDataDao {

    private JdbcTemplateImpl jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplateImpl jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplateImpl getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public List<TfuAlarmTitle> selectAll() {

        String SQL = "select title_id,vendor_id,title,insert_time,ne_type,vendor_alarm_id,confirmed,time_stamp from nmosdb.tfu_alarm_title";
        HashMap<String, Object> params = new HashMap<>();
        return jdbcTemplate.queryForList(SQL,params, TfuAlarmTitle.class);
    }


    @Override
    public List<PmmpErrorResult> selectAlldata() {
        String SQL = "select * from nmosdb.pmmp_error_result";
//        HashMap<String, Object> params = new HashMap<>();
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance (PmmpErrorResult.class));
    }

    /**
     * 解析有效字段批量入库
     * @param param
     * @return
     */
    @Override
    public Integer insertBatch(Map<String, Object> param) {
//        String sql = "INSERT INTO nmosdb.PMMP_ERROR_RESULT (topo_id,ne_id,ne_name,PORT_ID,PORT_NAME,PORT_TYPE,BANDWIDTH,TODAY_TIME,TODAY_IN_RATE,TODAY_OUT_RATE,YESTERDAY_TIME,YESTERDAY_IN_RATE,YESTERDAY_OUT_RATE,IN_EFFICIENCY,OUT_EFFICIENCY,IN_TONGBI,OUT_TONGBI,ALARM_REASON) VALUES \n" +
//                "(1,1,1,1,1,1,1,sysdate,1,1,sysdate-1,1,1,1,1,1,1,1)";
        String sql = "insert into nmosdb.tfu_alarm_title (title_id,vendor_id,title,insert_time,ne_type,vendor_alarm_id,confirmed,time_stamp) " +
                "values (:title_id,:vendor_id,:title,:insert_time,:ne_type,:vendor_alarm_id,:confirmed,:time_stamp)";
        int rowNum=0;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("title_id", param.get(""));
            map.put("vendor_id", param.get(""));
            map.put("title", param.get(""));
            map.put("insert_time", param.get(""));
            map.put("ne_type", param.get(""));
            map.put("vendor_alarm_id", param.get(""));
            map.put("confirmed", param.get(""));
            map.put("time_stamp", param.get(""));
            rowNum = jdbcTemplate.updateEx(sql, map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rowNum;
    }

}
