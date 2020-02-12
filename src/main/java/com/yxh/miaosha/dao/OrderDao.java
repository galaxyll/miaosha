package com.yxh.miaosha.dao;

import com.yxh.miaosha.domain.MiaoshaOrder;
import com.yxh.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author galaxy
 * @date 20-2-12 - 下午12:10
 */
@Mapper
public interface OrderDao {


    /**
     * 通过用户id和商品id获取秒杀订单信息并返回秒杀订单对象
     * 若返回null则说明该用户未参与该商品秒杀
     *
     * @param userId 用户Id
     * @param goodsId 商品Id
     * @return MiaoshaOrder
     */
    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    public MiaoshaOrder getMiaoshaOrderByUGId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    /**
     * 将订单对象插入数据库，返回订单编号
     *
     * @param order 订单对象
     * @return 生成的订单编号
     */
    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public long insertOrder(OrderInfo order);

    /**
     * 将秒杀订单对象插入数据库
     *
     * @param miaoshaOrder 秒杀订单对象
     * @return int
     */
    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
