package com.yxh.miaosha.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.yxh.miaosha.domain.MiaoshaGoods;
import com.yxh.miaosha.domain.MiaoshaOrder;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.rabbitmq.MQSender;
import com.yxh.miaosha.rabbitmq.MiaoshaMessage;
import com.yxh.miaosha.redis.RedisService;
import com.yxh.miaosha.redis.key.GoodsKey;
import com.yxh.miaosha.redis.key.MiaoshaKey;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.service.GoodsService;
import com.yxh.miaosha.service.MiaoshaService;
import com.yxh.miaosha.service.OrderService;
import com.yxh.miaosha.util.MD5Util;
import com.yxh.miaosha.util.UUIDUtil;
import com.yxh.miaosha.util.UserUtil;
import com.yxh.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
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


    @RequestMapping(value = "/{path}/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result miaosha( User user, @PathVariable("path")String path, @RequestParam("goodsId")Long goodsId){
        /*
          判断用户是否登录
         */
        if (user==null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        boolean check = checkPath(path,user.getId(),goodsId);
        if (!check){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        if (flagMap.get(goodsId)){
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        /*
          判断库存
          一次内存查询
         */
        long stock = redisService.decr(GoodsKey.msGoodsStock,String.valueOf(goodsId));
        if (stock<0){
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

    private boolean checkPath(String path, Long userId, Long goodsId) {
        if (path==null||userId==null||goodsId==null){
            return false;
        }
         return path.equals(redisService.get(MiaoshaKey.miaoshaPath,""+userId+goodsId,String.class));
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

    @RequestMapping("/path")
    @ResponseBody
    public Result<String> getMiaoshaPath(User user,
                                         @RequestParam("verifyCode")int verifyCode,
                                         @RequestParam("goodsId") long goodsId){
        if (user==null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        boolean verifyCheck = miaoshaService.checkVerifyCode(user,goodsId,verifyCode);
        if (!verifyCheck){
            return Result.error(CodeMsg.MIAOSHA_ERROR);
        }

        String path = MD5Util.md5(UUIDUtil.uuid());
        redisService.set(MiaoshaKey.miaoshaPath,""+user.getId()+goodsId,path);
        return Result.success(path);
    }

    @RequestMapping(value = "/verifyCode",method = RequestMethod.GET)
    @ResponseBody
    public Result getMiaoshaVerifyCode(HttpServletResponse response, User user,
                                               @RequestParam("goodsId") long goodsId){
        if (user==null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        BufferedImage image = miaoshaService.createVerifyCode(user,goodsId);

        try {
            OutputStream out = response.getOutputStream();
            ImageIO.write(image,"JPEG",out);
            out.flush();
            out.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success(CodeMsg.MIAOSHA_ERROR);
        }
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
