package com.yxh.miaosha.dao;

import com.yxh.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author galaxy
 * @date 20-2-9 - 下午10:27
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id=#{id}")
    public User getUserById(Long id);

}
