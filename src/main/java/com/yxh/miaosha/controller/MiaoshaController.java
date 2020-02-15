package com.yxh.miaosha.controller;

import com.yxh.miaosha.domain.MiaoshaOrder;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.service.GoodsService;
import com.yxh.miaosha.service.MiaoshaService;
import com.yxh.miaosha.service.OrderService;
import com.yxh.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public Result miaosha( User user, @RequestParam("goodsId")Long goodsId){
        if (user==null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        int stock = goods.getStockCount();
        if (stock<=0){
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }

        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUGId(user.getId(),goodsId);
        if (miaoshaOrder!=null){
            return Result.error(CodeMsg.MIAOSHA_REPEAT);
        }

        OrderInfo order = miaoshaService.miaosha(user,goods);

        return Result.success(order);
    }
}
