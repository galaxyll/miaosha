package com.yxh.miaosha.dao;

import com.yxh.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author galaxy
 * @date 20-2-9 - 下午10:27
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id=#{id}")
    public User getUserById(Long id);

    @Insert("insert into user(id,password,salt) values(#{mobile},#{dbPass},#{salt})")
    void insertUser(String mobile, String dbPass, String salt);
}
