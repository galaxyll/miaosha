package com.yxh.miaosha.service;

import com.yxh.miaosha.dao.UserDao;
import com.yxh.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author galaxy
 * @date 20-2-9 - 下午10:47
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getUserByName(int id){
        return userDao.getUserByName(id);
    }

    public boolean tx(User user,User user2){
        userDao.insertUser(user);
        userDao.insertUser(user2);
        return true;
    }
}
