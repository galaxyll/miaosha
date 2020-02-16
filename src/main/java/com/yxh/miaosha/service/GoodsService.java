package com.yxh.miaosha.service;

import com.yxh.miaosha.dao.GoodsDao;
import com.yxh.miaosha.domain.MiaoshaGoods;
import com.yxh.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author galaxy
 * @date 20-2-11 - 下午9:36
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoById(Long goodsId){
        return goodsDao.getGoodsVoById(goodsId);
    }

    public Boolean reduceStock(GoodsVo goodsVo) {

        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
        miaoshaGoods.setGoodsId(goodsVo.getId());

        int ret =  goodsDao.reduceStock(miaoshaGoods);
        return ret>0;
    }
}
