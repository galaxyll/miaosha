package com.yxh.miaosha.controller;

import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.redis.RedisService;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author galaxy
 * @date 20-2-9 - 下午2:06
 */
@Controller
@RequestMapping("/demo")
public class SimpleController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","yxh");
        return "hello";
    }
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        User user = new User();
        user.setId(2);
        user.setName("11");
        User user2 = new User();
        user2.setId(2);
        user2.setName("aa");
        userService.tx(user,user2);
        return Result.success("hello");
    }
    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/select")
    @ResponseBody
    public Result<String> getUserByName(){
        return Result.success(redisService.get("key1",String.class) );
    }
}
