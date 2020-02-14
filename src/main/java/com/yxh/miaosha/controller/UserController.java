package com.yxh.miaosha.controller;

import com.yxh.miaosha.service.UserService;
import com.yxh.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author galaxy
 * @date 20-2-11 - 上午9:21
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public void register(LoginVo loginVo){

        userService.register(loginVo);
    }

}
