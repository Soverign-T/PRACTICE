package com.boco.alarmtitle.controller;

import com.boco.alarmtitle.common.dao.MessageDataDao;
import com.boco.domain.TfuAlarmTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hao 2022/10/31 14:29
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
public class RestController {
    private MessageDataDao userDao;

    @Autowired
    public void setUserDao(MessageDataDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 批量插入(数据库)
     *
     * @return
     */
//    @RequestMapping("/batchUpdate")
//    public Integer batchUpdate(Map<String, String> param) {
//        return userDao.insertBatch(param);
//    }

    /**
     * 从缓存加载数据
     * @return
     */
    @GetMapping("/selectAll")
    public List<TfuAlarmTitle> selectAll() {
        return userDao.selectAll();
    }


}
