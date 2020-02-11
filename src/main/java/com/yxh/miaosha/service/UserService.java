package com.yxh.miaosha.service;

import com.yxh.miaosha.dao.UserDao;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.exception.GlobalException;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.util.MD5Util;
import com.yxh.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public boolean login(LoginVo loginVo){

        if (loginVo==null){
            throw new GlobalException(CodeMsg.PARAM_EMPTY);
        }

        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        if (mobile==null||formPass==null){
            throw  new GlobalException(CodeMsg.PARAM_EMPTY);
        }
        User user = getUserById(Long.valueOf(mobile));
        if (user==null){
            throw new GlobalException(CodeMsg.ACCOUNT_NOT_FOUND) ;
        }
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calPass = MD5Util.formPassToDBPass(formPass,dbSalt);
        if (dbPass.equals(calPass)){
            return true;
        }
        return false;
    }
}
