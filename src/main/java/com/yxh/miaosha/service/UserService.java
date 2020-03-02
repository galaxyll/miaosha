package com.yxh.miaosha.service;

import com.yxh.miaosha.dao.UserDao;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.exception.GlobalException;
import com.yxh.miaosha.redis.RedisService;
import com.yxh.miaosha.redis.key.UserKey;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.util.MD5Util;
import com.yxh.miaosha.util.UUIDUtil;
import com.yxh.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author galaxy
 * @date 20-2-9 - 下午10:47
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    RedisService redisService;

    public User getUserById(Long id){

        User user;
        user = redisService.get(UserKey.id,""+id.toString(),User.class);
        if (user!=null){
            return user;
        }

        user = userDao.getUserById(id);
        if (user!=null){
            redisService.set(UserKey.id,""+id,user);
        }
        return user;
    }

    public User getUserByToken(HttpServletResponse response,String token){
        if (token==null||token.isEmpty()){
            return null;
        }
        User user = redisService.get(UserKey.token,token,User.class);
        if (user==null){
            return null;
        }
        updateToken(response,token,user);
        return user;
    }

    public void updateToken(HttpServletResponse response,String token,User user){
        redisService.set(UserKey.token,token,user);
        Cookie cookie = new Cookie("token",token);
        cookie.setMaxAge(UserKey.token.getExpireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String login(HttpServletResponse response,LoginVo loginVo){
//
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
//
        User user = getUserById(Long.valueOf(mobile));
        if (user==null){
            throw new GlobalException(CodeMsg.ACCOUNT_NOT_FOUND) ;
        }

        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calPass = MD5Util.formPassToDBPass(formPass,dbSalt);
        if (!dbPass.equals(calPass)){
            return null;
        }
        String token = UUIDUtil.uuid();
        redisService.set(UserKey.token,token,user);
        Cookie cookie = new Cookie("token",token);
        cookie.setPath("/");
        cookie.setMaxAge(UserKey.token.getExpireSeconds());
        response.addCookie(cookie);
        return token;
    }

    public boolean register(LoginVo loginVo){
        String salt = "1a2b3d";
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        String dbPass = MD5Util.formPassToDBPass(formPass,salt);
        userDao.insertUser(mobile,dbPass,salt);
        return true;
    }
}
