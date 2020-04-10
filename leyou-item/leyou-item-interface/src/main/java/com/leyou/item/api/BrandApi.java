package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Gouzhong
 * @Description :
 * @date Create By Qingsong in 15:01 2019/8/16
 * @email : 1162864960@qq.com
 */
@RequestMapping("brand")
public interface BrandApi {

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id")Long id);

}

