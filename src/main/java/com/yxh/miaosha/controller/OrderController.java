package com.yxh.miaosha.controller;

import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.vo.OrderDetailVo;
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

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> orderDetail(User user,@RequestParam("orderId") long orderId){

       if (user==null){
           return Result.error(CodeMsg.SESSION_ERROR);
       }
       OrderDetailVo orderInfo = new OrderDetailVo();
       return Result.success(orderInfo);
    }
}
