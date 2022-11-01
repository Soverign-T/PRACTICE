package com.boco.alarmtitle.common.dao.Impl;

import com.boco.alarmtitle.common.dao.JdbcTemplateImpl;
import com.boco.alarmtitle.common.dao.UserDao;

import java.util.HashMap;
import java.util.List;

/**
 * @author hao 2022/11/1 14:17
 */
public class UserDaoImpl implements UserDao {

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
}
