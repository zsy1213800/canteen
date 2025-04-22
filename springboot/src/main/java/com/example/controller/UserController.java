package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.hutool.core.lang.Console.print;


/**
 * 商家管理相关接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //查询全部商家
    @GetMapping("/selectAll")
    public Result selectAll(User user){
        List<User> list = userService.selectAll(user);
        return Result.success(list);
    }
    //查询固定商家
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        User user = userService.selectById(id);
        if(ObjectUtil.isEmpty(user)){
            throw new CustomException("用户不存在");
        }
        return Result.success(user);
    }
    //分页查询
    @GetMapping("/selectPage")
    public Result selectPage(User user,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<User> pageInfo = userService.selectPage(user,pageNum,pageSize);
        return Result.success(pageInfo);
    }

    //添加
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        //数据校验
        if(ObjectUtil.isEmpty(user.getUsername())||ObjectUtil.isEmpty(user.getPassword())){
            return Result.error("用户名密码不能为空");
        }
        userService.add(user);
        return Result.success();
    }

    //修改
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }
    //批量删除
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        userService.deleteBatch(ids);
        return Result.success();
    }

}
