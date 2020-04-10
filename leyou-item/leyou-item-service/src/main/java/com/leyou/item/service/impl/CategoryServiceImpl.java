package com.leyou.item.service.impl;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.service.CategoryService;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gouzhong
 * @Description :
 * @date Create By Qingsong in 21:10 2019/8/15
 * @email : 1162864960@qq.com
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点查询子节点
     *
     * @param pid 父类ID
     * @return Category对象
     */
    @Override
    public List<Category> queryCategorysByPid(Long pid) {

        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }

    /**
     * 增加分类
     * @param category
     */
    @Override
    public void saveCatrgory(Category category) {

        category.setId(null);
        int insert = this.categoryMapper.insert(category);
        System.out.println(insert);
        Category Parent = new Category();
        Parent.setId(category.getParentId());
        Parent.setIsParent(true);
        this.categoryMapper.updateByPrimaryKeySelective(Parent);
    }

    @Override
    public List<String> queryNamesByIds(List<Long> ids) {
        List<Category> categories = this.categoryMapper.selectByIdList(ids);
        return categories.stream().map(category -> category.getName()).collect(Collectors.toList());
    }

}
