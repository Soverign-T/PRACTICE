package com.boco.alarmtitle.common.dao.Impl;

import com.boco.alarmtitle.common.dao.JdbcTemplateImpl;
import com.boco.alarmtitle.common.dao.MessageDataDao;
import com.boco.domain.PmmpErrorResult;
import com.boco.domain.TfuAlarmTitle;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
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
//title_id,vendor_id,title,insert_time,ne_type,vendor_alarm_id,confirmed,time_stamp
        String SQL = "select title_id,vendor_id,title,ne_type,vendor_alarm_id,confirmed from nmosdb.tfu_alarm_title";
//        HashMap<String, Object> params = new HashMap<>();
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(TfuAlarmTitle.class));
    }

    /**
     * 解析有效字段批量入库
     *
     * @param
     * @return
     */
    @Override
    public Integer insertBatch(List<TfuAlarmTitle> parms) {
        List<String> sqls = new ArrayList<>();
        String sql = "insert into nmosdb.tfu_alarm_title (title_id,vendor_id,title,insert_time,ne_type,vendor_alarm_id,confirmed,time_stamp) " +
                "values (:title_id,:vendor_id,:title,:null,:ne_type,:vendor_alarm_id,:confirmed,:null)";
        int rowNum = 0;
        try {
            List<Map<String, Object>> resultList = new ArrayList<>();
            for (TfuAlarmTitle tfuAlarmTitle: parms) {

                Map<String, Object> map = new HashMap<>();
                map.put("title_id", tfuAlarmTitle.getTitleId());
                map.put("vendor_id", tfuAlarmTitle.getVendorId());
                map.put("title", tfuAlarmTitle.getTitle());
//                map.put("insert_time", tfuAlarmTitle.getTitleId());
                map.put("ne_type", tfuAlarmTitle.getNeType());
                map.put("vendor_alarm_id", tfuAlarmTitle.getVendorAlarmId());
                map.put("confirmed", tfuAlarmTitle.getConfirmed());
//                map.put("time_stamp", tfuAlarmTitle.getTitleId());
                resultList.add(map);
                sqls.add(sql);
            }
            rowNum = jdbcTemplate.updateEx(sqls, resultList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rowNum;
    }

    @Override
    public Integer updateBatch(List<TfuAlarmTitle> params) {
        List<String> sqls = new ArrayList<>();
        String sql = "update nmosdb.tfu_alarm_title set title=:title";
        int rowNum = 0;
        try {
            List<Map<String, Object>> resultList = new ArrayList<>();
            for (TfuAlarmTitle tfuAlarmTitle: params) {

                Map<String, Object> map = new HashMap<>();
//                map.put("title_id", tfuAlarmTitle.getTitleId());
//                map.put("vendor_id", tfuAlarmTitle.getVendorId());
                map.put("title", "测试测试1111111111111111111111");
////                map.put("insert_time", tfuAlarmTitle.getTitleId());
//                map.put("ne_type", tfuAlarmTitle.getNeType());
//                map.put("vendor_alarm_id", tfuAlarmTitle.getVendorAlarmId());
//                map.put("confirmed", tfuAlarmTitle.getConfirmed());
////                map.put("time_stamp", tfuAlarmTitle.getTitleId());
                resultList.add(map);
                sqls.add(sql);
            }
            rowNum = jdbcTemplate.updateEx(sqls, resultList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rowNum;
    }

}
