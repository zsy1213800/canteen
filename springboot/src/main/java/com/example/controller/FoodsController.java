package com.example.controller;

import com.example.common.Result;
import com.example.entity.Foods;
import com.example.service.FoodsService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;

@RestController
@RequestMapping("/foods")
public class FoodsController {

    @Resource
    FoodsService foodsService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Foods foods) {
        foodsService.add(foods);
        Logger logger = LoggerFactory.getLogger(FoodsController.class);
        logger.info(foods.getBusinessname());
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        foodsService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        foodsService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Foods foods) {
        foodsService.updateById(foods);
        return Result.success();
    }

    /**
     * 查询单个
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Foods foods = foodsService.selectById(id);
        return Result.success(foods);
    }

    @GetMapping("/selectByBusinessname/{businessname}")
    public Result selectByBusinessname(@PathVariable String businessname) {
        Foods foods = foodsService.selectByBusinessname(businessname);
        return Result.success(foods);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(String name) {
        List<Foods> list = foodsService.selectAll(name);
        return Result.success(list);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectPage")
    public Result selectPage(
            String name,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Foods> pageInfo = foodsService.selectPage(name, pageNum, pageSize);
        Logger logger = LoggerFactory.getLogger(FoodsController.class);
        logger.info(name);
        return Result.success(pageInfo);
    }

    /**
     * 查询某商家所有
     */
    @GetMapping("/selectbusinessnamePage")
    public Result selectbusinessnamePage(
            String name,
            String businessname,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Foods> pageInfo = foodsService.selectbusinessnamePage(businessname, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
