package com.yxh.miaosha.controller;

import com.yxh.miaosha.rabbitmq.MQSender;
import com.yxh.miaosha.service.UserService;
import com.yxh.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author galaxy
 * @date 20-2-11 - 上午9:21
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MQSender sender;

    @RequestMapping("/do_register")
    public void register(LoginVo loginVo){

        userService.register(loginVo);
    }

//    @RequestMapping("/queue")
//    @ResponseBody
//    public void send1(){
//        sender.send2("hello galaxy");
//    }

}
