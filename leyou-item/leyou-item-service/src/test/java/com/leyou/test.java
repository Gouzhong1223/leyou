package com.leyou;

import com.leyou.item.controller.CategoryController;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

/**
 * @author Gouzhong
 * @Description :
 * @date Create By Qingsong in 22:18 2019/8/15
 * @email : 1162864960@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class test {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void test() {

        List<Category> categories = categoryService.queryCategorysByPid(0L);
        for (Category category : categories) {
            System.out.println(categories);
        }
    }

    @Autowired
    private CategoryController categoryController;



}
