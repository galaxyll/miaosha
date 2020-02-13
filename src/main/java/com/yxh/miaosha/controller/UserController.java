package com.yxh.miaosha.controller;

import com.yxh.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author galaxy
 * @date 20-2-11 - 上午9:21
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;


}
