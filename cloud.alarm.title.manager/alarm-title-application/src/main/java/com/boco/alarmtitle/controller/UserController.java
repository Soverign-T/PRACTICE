package com.boco.alarmtitle.controller;

import com.boco.alarmtitle.common.cache.DispMessageEntityCache;
import com.boco.alarmtitle.common.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author hao 2022/10/31 14:29
 */
@RestController
@RequestMapping("/")
public class UserController {
    private MessageDao userDao;
    private DispMessageEntityCache dispMessageEntityCache;

    @Autowired
    public void setDispMessageEntityCache(DispMessageEntityCache dispMessageEntityCache) {
        this.dispMessageEntityCache = dispMessageEntityCache;
    }

    @Autowired
    public void setUserDao(MessageDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/zhangsan")
    public Integer selectAll() {
        return userDao.insertBatch();
    }

    @RequestMapping("/lisi")
    public List<Map<String, Object>> selectAllaa() {
        return dispMessageEntityCache.getData();
    }
}
