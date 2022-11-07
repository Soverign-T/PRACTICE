package com.boco.alarmtitle.controller;

import com.boco.alarmtitle.common.cache.TitleTextCache;
import com.boco.alarmtitle.common.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author hao 2022/10/31 14:29
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
public class RestController {
    private MessageDao userDao;
    private TitleTextCache titleTextCache;

    @Autowired
    public void setTitleTextCache(TitleTextCache titleTextCache) {
        this.titleTextCache = titleTextCache;
    }

    @Autowired
    public void setUserDao(MessageDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 批量插入(数据库)
     * @return
     */
    @RequestMapping("/batchUpdate")
    public Integer batchUpdate() {
        return userDao.insertBatch();
    }

    /**
     * 从缓存加载数据
     * @return
     */
    @GetMapping("/selectAll")
    public List<Map<String, Object>> selectAll() {
        return titleTextCache.getData();
    }
}
