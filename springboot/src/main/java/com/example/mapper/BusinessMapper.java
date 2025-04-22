package com.example.mapper;

import com.example.entity.Admin;
import com.example.entity.Business;
import com.example.entity.Tables;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商家相关的业务方法
 */
@Service
public interface BusinessMapper {

    @Select("select * from business where username = #{username}")
    Business selectByUsername(String username);

    List<Business> selectAll(Business business);

    int insert(Business business);

    int updateById(Business business);

    int deleteById(Integer id);

    @Select("select * from business where id = #{id}")
    Business selectById(Integer id);
}
