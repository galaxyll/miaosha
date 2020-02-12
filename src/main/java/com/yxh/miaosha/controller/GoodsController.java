package com.yxh.miaosha.controller;

import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.service.GoodsService;
import com.yxh.miaosha.service.UserService;
import com.yxh.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author galaxy
 * @date 20-2-11 - 下午3:53
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toList(Model model,User user)
    {

        model.addAttribute("user",user);

        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsVos);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, User user, @PathVariable("goodsId")Long goodsId){
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        model.addAttribute("goods",goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        /**
         * 0:未开始 1：进行中 2：已结束
         */
        int miaoshaStatus = 0;
        int remianSeconds = 0;

        if (now<startTime){
            miaoshaStatus = 0;
            remianSeconds = (int) (startTime-now)/1000;
        }else if (now>endTime){
            miaoshaStatus = 2;
            remianSeconds = -1;
        }else {
            miaoshaStatus = 1;
            remianSeconds = 0;
        }
        model.addAttribute("remainSeconds",remianSeconds);
        model.addAttribute("miaoshaStatus",miaoshaStatus);

        return "goods_detail";
    }

}
