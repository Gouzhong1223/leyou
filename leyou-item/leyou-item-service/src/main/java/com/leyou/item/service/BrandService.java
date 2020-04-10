package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

/**
 * @author Gouzhong
 * @Description :
 * @date Create By Qingsong in 15:01 2019/8/16
 * @email : 1162864960@qq.com
 */
public interface BrandService {

    PageResult<Brand> queryBrandsByPages(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    void saveBrand(Brand brand, List<Long> cids);

    List<Brand> queryBrandsByCid(Long cid);

    Brand queryBrandsById(Long id);
}
