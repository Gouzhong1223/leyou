package com.leyou.pojo;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;
import java.util.Map;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-21 21:09
 * @email : 1162864960@qq.com
 */
public class SearchResult extends PageResult<Goods> {

    private List<Map<String, Object>> categories;
    private List<Brand> brands;
    private List<Map<String,Object>> spec;

    public SearchResult(Long total, Integer totalpage, List<Goods> items, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> spec) {
        super(total, totalpage, items);
        this.categories = categories;
        this.brands = brands;
        this.spec = spec;
    }

    public SearchResult(Long total, List<Goods> items, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> spec) {
        super(total, items);
        this.categories = categories;
        this.brands = brands;
        this.spec = spec;
    }

    public SearchResult(List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> spec) {
        this.categories = categories;
        this.brands = brands;
        this.spec = spec;
    }

    public List<Map<String, Object>> getCategories() {
        return categories;
    }

    public void setCategories(List<Map<String, Object>> categories) {
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Map<String, Object>> getSpec() {
        return spec;
    }

    public void setSpec(List<Map<String, Object>> spec) {
        this.spec = spec;
    }
}
