package com.yxh.miaosha.controller;

import com.yxh.miaosha.domain.MiaoshaGoods;
import com.yxh.miaosha.domain.MiaoshaOrder;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.rabbitmq.MQSender;
import com.yxh.miaosha.rabbitmq.MiaoshaMessage;
import com.yxh.miaosha.redis.RedisService;
import com.yxh.miaosha.redis.key.GoodsKey;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.service.GoodsService;
import com.yxh.miaosha.service.MiaoshaService;
import com.yxh.miaosha.service.OrderService;
import com.yxh.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author galaxy
 * @date 20-2-12 - 上午11:44
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;

    private Map<Long,Boolean> flagMap = new HashMap<>();


    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result miaosha( User user, @RequestParam("goodsId")Long goodsId){
        /*
          判断用户是否登录
         */
        if (user==null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        if (flagMap.get(goodsId)){
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        /*
          判断库存
          一次内存查询
         */
        long stock = redisService.decr(GoodsKey.msGoodsStock,String.valueOf(goodsId));
        if (stock<=0){
            flagMap.put(goodsId,true);
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }

        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUGId(user.getId(),goodsId);
        if (miaoshaOrder!=null){
            return Result.error(CodeMsg.MIAOSHA_REPEAT);
        }

        /*
          发送任务到队列
         */
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(mm);
        /*
            0代表排队中
         */
        return Result.success(0);

//        /*
//          生成并获取订单信息
//          三次数据库操作（此处订单只进行一次没必要缓存）
//         */
//        OrderInfo order = miaoshaService.miaosha(user,goodsId);
//
//        /*
//          返回订单信息
//         */
//        return Result.success(order);
    }

    /**
     * 0:排队中
     * -1:失败
     * orderId :成功 （订单号>0）
     * @return 秒杀状态
     */
    @RequestMapping("/result")
    @ResponseBody
    public Result<Long> result(User user,@RequestParam("goodsId")long goodsId){
        long resultStatus = miaoshaService.getMiaoshaResult(user.getId(),goodsId);
        return Result.success(resultStatus);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();

        if(goodsVos!=null){
            for (GoodsVo goods:
                 goodsVos) {
                redisService.set(GoodsKey.msGoodsStock,""+goods.getId(),goods.getStockCount());
                flagMap.put(goods.getId(),false);
            }
        }


    }
}
