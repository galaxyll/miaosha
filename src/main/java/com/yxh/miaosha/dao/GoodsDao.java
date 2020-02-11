package com.yxh.miaosha.dao;

import com.yxh.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author galaxy
 * @date 20-2-11 - 下午9:37
 */
@Mapper
public interface GoodsDao {

    /**
     * 返回秒杀商品及其所有属性的GVO对象
     *
     * miaoshagoods表左连接goods表
     * @return GoodsVo
     */
    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join " +
            "goods g on mg" +
            ".goods_id=g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join " +
            "goods g on mg" +
            ".goods_id=g.id where g.id=#{goodsId}")
    public GoodsVo getGoodsVoById(Long goodsId);


}
