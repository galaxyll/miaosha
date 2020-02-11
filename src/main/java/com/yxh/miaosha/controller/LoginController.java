package com.yxh.miaosha.controller;

import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.service.UserService;
import com.yxh.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author galaxy
 * @date 20-2-10 - 下午9:06
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<CodeMsg> doLogin(@Valid LoginVo loginVo){

        userService.login(loginVo);
        return Result.success(CodeMsg.SUCCESS);
    }
}
