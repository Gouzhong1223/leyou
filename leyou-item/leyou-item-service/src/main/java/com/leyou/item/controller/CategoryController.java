package com.leyou.item.controller;

import com.leyou.item.service.CategoryService;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Gouzhong
 * @Description :
 * @date Create By Qingsong in 21:12 2019/8/15
 * @email : 1162864960@qq.com
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点的Id查询子节点
     *
     * @param pid
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategorysByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid) {

        try {
            //判断传入参数是否为空
            if (pid == null || pid < 0) {
                //非法请求
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            List<Category> categories = this.categoryService.queryCategorysByPid(pid);
            //判断查询结果是否为空
            if (CollectionUtils.isEmpty(categories)) {
                //404：资源服务器未找到
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            //200：请求成功
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //500：服务器内部错误
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PostMapping
    public ResponseEntity<Void> saveCatrgory(Category category) {
        this.categoryService.saveCatrgory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 根据id查询名称
     * @param ids
     * @return
     */
    @GetMapping
    public ResponseEntity<List<String>> queryNamesBuId(@RequestParam("ids") List<Long> ids) {
        List<String> namesByIds = this.categoryService.queryNamesByIds(ids);
        if (CollectionUtils.isEmpty(namesByIds)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(namesByIds);
    }


}
