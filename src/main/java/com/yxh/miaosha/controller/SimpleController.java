package com.yxh.miaosha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author galaxy
 * @date 20-2-9 - 下午2:06
 */
@Controller
@RequestMapping("/demo")
public class SimpleController {
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","yxh");
        return "hello";
    }
}
