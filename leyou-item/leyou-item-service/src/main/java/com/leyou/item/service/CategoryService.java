package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * @author Gouzhong
 * @Description :
 * @date Create By Qingsong in 21:09 2019/8/15
 * @email : 1162864960@qq.com
 */
public interface CategoryService {
    /**
     * 查询所有的商品分类
     *
     * @param pid 父类ID
     * @return Category对象
     */
    List<Category> queryCategorysByPid(Long pid);

    void saveCatrgory(Category category);

    public List<String> queryNamesByIds(List<Long> ids);
}
