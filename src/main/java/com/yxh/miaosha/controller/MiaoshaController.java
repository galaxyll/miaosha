package com.yxh.miaosha.controller;

import com.yxh.miaosha.domain.MiaoshaOrder;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.service.GoodsService;
import com.yxh.miaosha.service.MiaoshaService;
import com.yxh.miaosha.service.OrderService;
import com.yxh.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author galaxy
 * @date 20-2-12 - 上午11:44
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String miaosha(Model model, User user, @RequestParam("goodsId")Long goodsId){
        if (user==null){
            return "login";
        }

        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        int stock = goods.getStockCount();
        if (stock<=0){
            model.addAttribute("errmsg", CodeMsg.MIAOSHA_OVER.getMsg());
            return "miaosha_fail";
        }
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUGId(user.getId(),goodsId);
        if (miaoshaOrder!=null){
            model.addAttribute("errmsg", CodeMsg.MIAOSHA_REPEAT.getMsg());
        }

        OrderInfo order = miaoshaService.miaosha(user,goods);
        model.addAttribute("orderInfo",order);
        model.addAttribute("goods",goods);

        return "order_detail";
    }
}
