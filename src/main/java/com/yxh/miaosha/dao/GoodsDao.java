package com.yxh.miaosha.dao;

import com.yxh.miaosha.domain.MiaoshaGoods;
import com.yxh.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    /**
     * 通过商品id获取商品所有属性的GVO对象
     *
     * @param goodsId 商品id
     * @return GoodsVo
     */
    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join " +
            "goods g on mg" +
            ".goods_id=g.id where g.id=#{goodsId}")
    public GoodsVo getGoodsVoById(Long goodsId);

    /**
     * 把指定秒杀商品的库存减一
     *
     * @param goodsVo 秒杀商品信息
     */

    @Update("update miaosha_goods set stock_count=stock_count-1 where goods_id=#{goodsId}")
    void reduceStock(MiaoshaGoods goodsVo);
}
