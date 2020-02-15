package com.yxh.miaosha.controller;

import com.yxh.miaosha.domain.Goods;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.service.GoodsService;
import com.yxh.miaosha.service.OrderService;
import com.yxh.miaosha.vo.GoodsVo;
import com.yxh.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author galaxy
 * @date 20-2-15 - 下午12:24
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> orderDetail(User user,@RequestParam("orderId") long orderId){

       if (user==null){
           return Result.error(CodeMsg.SESSION_ERROR);
       }

       OrderInfo order = orderService.getOrderInfoById(orderId);
       if (order==null){
           return Result.error(CodeMsg.ORDER_NOT_EXIST);
       }

       long goodsId = order.getGoodsId();
       GoodsVo goods= goodsService.getGoodsVoById(goodsId);
       OrderDetailVo vo = new OrderDetailVo();
       vo.setGoods(goods);
       vo.setOrderInfo(order);
       return Result.success(vo);
    }
}
