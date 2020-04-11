package com.leyou.item.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Gouzhong
 * @Description :
 * @date Create By Qingsong in 21:12 2019/8/15
 * @email : 1162864960@qq.com
 */
@RequestMapping("/category")
public interface CategoryApi {

    /**
     * 根据id查询名称
     *
     * @param ids
     * @return
     */
    @GetMapping
    List<String> queryNamesBuId(@RequestParam("ids") List<Long> ids);

}
