package com.yxh.miaosha.service;

import com.yxh.miaosha.dao.UserDao;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.util.MD5Util;
import com.yxh.miaosha.vo.LoginVo;
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
    private UserDao userDao;

    public User getUserById(Long id){
        return userDao.getUserById(id);
    }

    public CodeMsg login(LoginVo loginVo){

        if (loginVo==null){
            return CodeMsg.PARAM_EMPTY;
        }

        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        if (mobile==null||formPass==null){
            return CodeMsg.PARAM_EMPTY;
        }

        User user = getUserById(Long.valueOf(mobile));
        System.out.println(user);
        if (user==null){
            return CodeMsg.ACCOUNT_NOT_FOUND;
        }
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calPass = MD5Util.formPassToDBPass(formPass,dbSalt);
        if (dbPass.equals(calPass)){
            return CodeMsg.SUCCESS;
        }
        return CodeMsg.PARAM_ERROR;
    }
}
