package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.Business;
import com.example.entity.Tables;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.example.mapper.BusinessMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static cn.hutool.core.lang.Console.print;

/**
 * 商家相关的业务方法
 */
@Service
public class BusinessService {

    @Resource(name = "businessMapper")
    BusinessMapper businessMapper;


    public Account login(Account account) {
        String username = account.getUsername();
        // 根据账号查询数据
        Business dbbusiness = businessMapper.selectByUsername(username);
        if (dbbusiness == null) {
            throw new CustomException("账号不存在");
        }
        // 校验密码
        if (!dbbusiness.getPassword().equals(account.getPassword())) {
            throw new CustomException("账号或者密码错误");
        }
        return dbbusiness;
    }

    //查询所有
    public List<Business> selectAll(Business business){
        return businessMapper.selectAll(business);
    }

    //添加商家
    public void add(Business business){
        //用户名不能重复
        Business jBusiness = this.selectByUsername(business.getUsername());
        //如果根据新增数据查到了用户名，则用户名重复，不允许插入
        if(ObjectUtil.isNotEmpty(jBusiness)){
            throw new CustomException("请不要输入重复的用户名");
        }
        if(ObjectUtil.isEmpty(business.getPassword())){
            business.setPassword("123");
        }
        if(ObjectUtil.isEmpty(business.getName())){
            business.setName(business.getUsername());
        }
        business.setRole(RoleEnum.BUSINESS.name());
        businessMapper.insert(business);
    }

    /**
     * 检验用户名是否重复
     */
    public Business selectByUsername(String username){
        Business params = new Business();
        params.setUsername(username);
        List<Business> list = this.selectAll(params);
        return list.size() == 0 ? null : list.get(0);
    }

    /**
     * 检验用户名是否与原来一致，根据id查询
     */
    public Business selectById(Integer id){
        Business params = new Business();
        params.setId(id);
        List<Business> list = this.selectAll(params);
        return list.size() == 0 ? null : list.get(0);
    }

    //更新商家数据
    public void updateById(Business business){
        //根据id查询商家是否存在
        Business jBusiness2 = this.selectById(business.getId());
        if(ObjectUtil.isEmpty(jBusiness2)){
            throw new CustomException("用户不存在");
        }
        //根据当前更新的商家查询数据集，如果数据集存在和待更新的商家一样的数据，那么当前更新作废
        Business jBusiness1 = this.selectByUsername(business.getUsername());
        businessMapper.updateById(business);
    }

    //删除商家
    public void deleteById(Integer id){
        businessMapper.deleteById(id);
    }
    //批量删除
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids){
            this.deleteById(id);
        }
    }

    //分页查询
    public PageInfo<Business> selectPage(Business business, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Business> list = businessMapper.selectAll(business);
        return PageInfo.of(list);
    }
}
