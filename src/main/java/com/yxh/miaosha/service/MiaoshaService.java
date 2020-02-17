package com.yxh.miaosha.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yxh.miaosha.domain.MiaoshaOrder;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.redis.RedisService;
import com.yxh.miaosha.redis.key.GoodsKey;
import com.yxh.miaosha.redis.key.MiaoshaKey;
import com.yxh.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author galaxy
 * @date 20-2-12 - 下午12:05
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(User user, GoodsVo goodsVo){

        Boolean success = goodsService.reduceStock(goodsVo);
        if (success){
            return orderService.createOrder(user,goodsVo);
        }else {
            setGoodsOver(goodsVo.getId());
            return null;
        }

    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(GoodsKey.goodsIsOver,goodsId.toString(),true);
    }

    private Boolean getGoodsOver(long goodsId) {
        return redisService.exists(GoodsKey.goodsIsOver,""+goodsId);
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUGId(userId,goodsId);
        if (order!=null){
            return order.getOrderId();
        }
        Boolean isOver = getGoodsOver(goodsId);
        if (isOver){
            return -1;
        }else {
            return 0;
        }
    }

    public BufferedImage createVerifyCode(User user, long goodsId) {
        if(user == null || goodsId <=0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.miaoshaVerifyCode, user.getId()+","+goodsId, rnd);
        //输出图片
        return image;
    }

    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkVerifyCode(User user, long goodsId, int verifyCode) {
        if(user == null || goodsId <=0) {
            return false;
        }
        Integer codeOld = redisService.get(MiaoshaKey.miaoshaVerifyCode, user.getId()+","+goodsId, Integer.class);
        if(codeOld == null || codeOld - verifyCode != 0 ) {
            return false;
        }
        redisService.delete(MiaoshaKey.miaoshaVerifyCode, user.getId()+","+goodsId);
        return true;
    }

    private static char[] ops = new char[] {'+', '-', '*'};
}
