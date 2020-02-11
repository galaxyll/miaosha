package com.yxh.miaosha.controller;

import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.service.UserService;
import com.yxh.miaosha.util.ValidatoUtil;
import com.yxh.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Result<CodeMsg> doLogin(LoginVo loginVo){

        if (loginVo.getMobile()==null||loginVo.getPassword()==null){
            return Result.error(CodeMsg.PARAM_EMPTY);
        }

        if (!ValidatoUtil.isMobile(loginVo.getMobile())){
            return Result.error(CodeMsg.PARAM_ERROR);
        }

        CodeMsg codeMsg = userService.login(loginVo);

        if (codeMsg.getCode()==0){
            return Result.success(CodeMsg.SUCCESS);
        }
        return Result.error(codeMsg);
    }
}
