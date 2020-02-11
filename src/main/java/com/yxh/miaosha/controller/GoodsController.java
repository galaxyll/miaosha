package com.yxh.miaosha.controller;

import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author galaxy
 * @date 20-2-11 - 下午3:53
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    UserService userService;

    @RequestMapping("/to_list")
    public String toList(Model model,User user)
    {
        if (user==null){
            return "login";
        }
        model.addAttribute("user",user);
        return "goods_list";
    }

}
