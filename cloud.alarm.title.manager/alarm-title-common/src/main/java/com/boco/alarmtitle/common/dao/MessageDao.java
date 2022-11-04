package com.boco.alarmtitle.common.dao;

import java.util.List;
import java.util.Map;

/**
 * @author hao 2022/11/1 14:17
 */
public interface MessageDao {
    List<Map<String, Object>> selectAll();

    Integer insertBatch();

}
