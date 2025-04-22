package com.example.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.RoleEnum;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static cn.hutool.core.lang.Console.print;

/**
 * 用户相关的业务方法
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public Account login(Account account) {
        String username = account.getUsername();
        // 根据账号查询数据
        User dbUser = userMapper.selectByUsername(username);
        if (dbUser == null) {
            throw new CustomException("账号不存在");
        }
        // 校验密码
        if (!dbUser.getPassword().equals(account.getPassword())) {
            throw new CustomException("账号或者密码错误");
        }
        return dbUser;
    }

    /**
     * 注册
     */
    public void register(User user) {
        // 1. 校验用户账号是否存在
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null) {
            throw new CustomException("账号已存在");
        }
        // 校验密码是否为空
        if (ObjectUtil.isEmpty(user.getPassword())) {
            throw new CustomException("密码不能为空");
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());  // 设置用户的名称为账号名称
        }
        user.setRole(RoleEnum.USER.name());
        userMapper.insert(user);
    }

    //查询所有
    public List<User> selectAll(User user){
        return userMapper.selectAll(user);
    }

    //添加用户
    public void add(User user){
        //用户名不能重复
        User jUser = this.selectByUsername(user.getUsername());
        //如果根据新增数据查到了用户名，则用户名重复，不允许插入
        if(ObjectUtil.isNotEmpty(jUser)){
            throw new CustomException("请不要输入重复的用户名");
        }
        if(ObjectUtil.isEmpty(user.getPassword())){
            user.setPassword("123");
        }
        if(ObjectUtil.isEmpty(user.getName())){
            user.setName(user.getUsername());
        }
        user.setRole(RoleEnum.BUSINESS.name());
        userMapper.insert(user);
    }

    /**
     * 检验用户名是否重复
     */
    public User selectByUsername(String username){
        User params = new User();
        params.setUsername(username);
        List<User> list = this.selectAll(params);
        return list.size() == 0 ? null : list.get(0);
    }

    /**
     * 检验用户名是否与原来一致，根据id查询
     */
    public User selectById(Integer id){
        User params = new User();
        params.setId(id);
        List<User> list = this.selectAll(params);
        return list.size() == 0 ? null : list.get(0);
    }

    //更新用户数据
    public void updateById(User user){
        //根据当前更新的用户查询数据集，如果数据集存在和待更新的用户一样的数据，那么当前更新作废
        User jUser1 = this.selectByUsername(user.getUsername());
        if(ObjectUtil.isNotEmpty(jUser1) && !Objects.equals(jUser1.getId(),user.getId())){
            throw new CustomException("用户名已存在");
        }
        userMapper.updateById(user);
    }

    //删除用户
    public void deleteById(Integer id){
        userMapper.deleteById(id);
    }
    //批量删除
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids){
            this.deleteById(id);
        }
    }

    //分页查询
    public PageInfo<User> selectPage(User user,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }
}
