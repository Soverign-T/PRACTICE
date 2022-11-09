package com.boco.alarmtitle.common.dao;

import com.boco.domain.PmmpErrorResult;
import com.boco.domain.TfuAlarmTitle;

import java.util.List;
import java.util.Map;

/**
 * @author hao 2022/11/1 14:17
 */
public interface MessageDataDao {
    List<TfuAlarmTitle> selectAll();

    Integer insertBatch(List<TfuAlarmTitle> parms);

    Integer updateBatch(List<TfuAlarmTitle> parms);

}
