package com.boco.alarmtitle.controller;

import com.boco.alarmtitle.common.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hao 2022/10/31 14:29
 */
@RestController
@RequestMapping("/")
public class UserController {
    private MessageDao userDao;

    @Autowired
    public void setUserDao(MessageDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/zhangsan")
    public Integer selectAll() {
        return userDao.insertBatch();
    }
}
