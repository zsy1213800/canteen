package com.example.controller;

import com.example.common.Result;
import com.example.entity.Tables;
import com.example.service.TablesService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TablesController {

    @Resource
    TablesService tablesService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Tables tables) {
        tablesService.add(tables);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        tablesService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        tablesService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Tables tables) {
        tablesService.updateById(tables);
        return Result.success();
    }

    @PutMapping("/addOrder")
    public Result addOrder(@RequestBody Tables tables) {
        tablesService.addOrder(tables);
        return Result.success();
    }

    @PutMapping("/removeOrder")
    public Result removeOrder(@RequestBody Tables tables) {
        tablesService.removeOrder(tables);
        return Result.success();
    }

    /**
     * 查询单个
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Tables tables = tablesService.selectById(id);
        return Result.success(tables);
    }

    @GetMapping("/selectByUserId/{userId}")
    public Result selectByUserId(@PathVariable Integer userId) {
        Tables tables = tablesService.selectByUserId(userId);
        return Result.success(tables);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(String name) {
        List<Tables> list = tablesService.selectAll(name);
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
        PageInfo<Tables> pageInfo = tablesService.selectPage(name, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
