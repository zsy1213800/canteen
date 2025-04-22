package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商家相关的业务方法
 */
@Service
public interface UserMapper {

    List<User> selectAll(User user);

    int insert(User user);

    int updateById(User user);

    int deleteById(Integer id);

    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);
}
