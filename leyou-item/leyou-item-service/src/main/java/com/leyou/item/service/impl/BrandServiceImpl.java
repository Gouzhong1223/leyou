package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * @author Gouzhong
 * @Description :
 * @date Create By Qingsong in 15:02 2019/8/16
 * @email : 1162864960@qq.com
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据查询条件查询并分页查询数据
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @Override
    public PageResult<Brand> queryBrandsByPages(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        //添加分页查询
        PageHelper.startPage(page, rows);

        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example);

        //包装PageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        //包装成分页结果集
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());


    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @Override
    public void saveBrand(Brand brand, List<Long> cids) {
        int i = this.brandMapper.insertSelective(brand);
        for (Long cid : cids) {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        }
    }

    /**
     * 根据分类id查询品牌列表
     *
     * @param cid
     * @return
     */
    @Override
    public List<Brand> queryBrandsByCid(Long cid) {
        return this.brandMapper.selectBrandsByCid(cid);
    }

    /**
     * 根据id查询品牌
     *
     * @param id
     * @return
     */
    @Override
    public Brand queryBrandsById(Long id) {
        return this.brandMapper.selectByPrimaryKey(id);
    }
}
