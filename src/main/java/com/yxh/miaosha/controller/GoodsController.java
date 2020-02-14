package com.yxh.miaosha.controller;

import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.redis.RedisService;
import com.yxh.miaosha.redis.key.GoodsKey;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.service.GoodsService;
import com.yxh.miaosha.service.UserService;
import com.yxh.miaosha.vo.GoodsDetailVo;
import com.yxh.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    RedisService redisService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping("/to_list")
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response)
    {

        model.addAttribute("user",user);
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsVos);
//        return "goods_list";
        String html;
        html = redisService.get(GoodsKey.goodsListKey,"",String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }

        SpringWebContext context = new SpringWebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap(),applicationContext);

        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",context);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.goodsListKey,"",html);
        }
        return html;

    }

//    @RequestMapping("/to_detail/{goodsId}")
//    @ResponseBody
//    public String detail(Model model, User user, @PathVariable("goodsId")Long goodsId,HttpServletRequest request, HttpServletResponse response){
//
//        model.addAttribute("user",user);
//
//        String html;
//        html = redisService.get(GoodsKey.goodsDetailKey,goodsId.toString(),String.class);
//        if (!StringUtils.isEmpty(html)){
//            return html;
//        }
//
//        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
//        model.addAttribute("goods",goods);
//
//        long startTime = goods.getStartDate().getTime();
//        long endTime = goods.getEndDate().getTime();
//        long now = System.currentTimeMillis();
//
//        /**
//         * 0:未开始 1：进行中 2：已结束
//         */
//        int miaoshaStatus ;
//        int remianSeconds ;
//
//        if (now<startTime){
//            miaoshaStatus = 0;
//            remianSeconds = (int) (startTime-now)/1000;
//        }else if (now>endTime){
//            miaoshaStatus = 2;
//            remianSeconds = -1;
//        }else {
//            miaoshaStatus = 1;
//            remianSeconds = 0;
//        }
//        model.addAttribute("remainSeconds",remianSeconds);
//        model.addAttribute("miaoshaStatus",miaoshaStatus);
//        SpringWebContext context = new SpringWebContext(request,response,request.getServletContext(),
//                request.getLocale(),model.asMap(),applicationContext);
//
//        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",context);
//        if (!StringUtils.isEmpty(html)){
//            redisService.set(GoodsKey.goodsDetailKey,goodsId.toString(),html);
//        }
//        return html;
//
//
//
//
////        return "goods_detail";
//    }

    @RequestMapping("/to_detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(Model model, User user, @PathVariable("goodsId")Long goodsId, HttpServletRequest request,
                                        HttpServletResponse response){

        GoodsVo goods = goodsService.getGoodsVoById(goodsId);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        /**
         * 0:未开始 1：进行中 2：已结束
         */
        int miaoshaStatus ;
        int remianSeconds ;

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
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goods);
        goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVo.setRemianSeconds(remianSeconds);
        goodsDetailVo.setUser(user);
        return Result.success(goodsDetailVo);
    }

}
