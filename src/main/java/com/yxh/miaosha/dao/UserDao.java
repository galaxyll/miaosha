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
@Repository
@Mapper
public interface UserDao {
    /**
     * 查找并返回指定name的User对象
     *
     * @param name username
     * @return User
     */
    @Select("select id,name from user where id = #{id}")
    User getUserByName(@Param("id") int id);

    @Insert("insert into user(id,name) values(#{id},#{name})")
    int insertUser(User user);
}
