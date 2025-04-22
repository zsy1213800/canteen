package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.Business;
import com.example.exception.CustomException;
import com.example.service.BusinessService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.hutool.core.lang.Console.print;


/**
 * 商家管理相关接口
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private BusinessService businessService;

    //查询全部商家
    @GetMapping("/selectAll")
    public Result selectAll(Business business){
        List<Business> list = businessService.selectAll(business);
        return Result.success(list);
    }
    //查询固定商家
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Business business = businessService.selectById(id);
        if(ObjectUtil.isEmpty(business)){
            throw new CustomException("用户不存在");
        }
        return Result.success(business);
    }
    //分页查询
    @GetMapping("/selectPage")
    public Result selectPage(Business business,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Business> pageInfo = businessService.selectPage(business,pageNum,pageSize);
        return Result.success(pageInfo);
    }

    //添加
    @PostMapping("/add")
    public Result add(@RequestBody Business business){
        //数据校验
        if(ObjectUtil.isEmpty(business.getUsername())||ObjectUtil.isEmpty(business.getPassword())){
            return Result.error("用户名密码不能为空");
        }
        businessService.add(business);
        return Result.success();
    }

    //修改
    @PutMapping("/update")
    public Result update(@RequestBody Business business){
        businessService.updateById(business);
        return Result.success();
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        businessService.deleteById(id);
        return Result.success();
    }
    //批量删除
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        businessService.deleteBatch(ids);
        return Result.success();
    }





}
