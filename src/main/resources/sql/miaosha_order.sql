CREATE TABLE `miaosha_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_uid_gid` (`user_id`,`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1551 DEFAULT CHARSET=utf8mb4;


INSERT INTO `miaosha_order` VALUES ('1547', '18912341234', '1561', '1');
INSERT INTO `miaosha_order` VALUES ('1548', '18912341234', '1562', '2');
INSERT INTO `miaosha_order` VALUES ('1549', '18912341234', '1563', '4');
INSERT INTO `miaosha_order` VALUES ('1550', '18912341234', '1564', '3');