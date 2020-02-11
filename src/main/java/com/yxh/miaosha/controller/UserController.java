package com.yxh.miaosha.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.service.UserService;
import com.yxh.miaosha.util.MD5Util;
import com.yxh.miaosha.vo.LoginVo;
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
