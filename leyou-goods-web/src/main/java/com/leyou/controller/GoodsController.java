package com.leyou.controller;

import com.leyou.service.GoodsHtmlService;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-22 21:29
 * @email : 1162864960@qq.com
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;


    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable Long id, Model model) {
        Map<String, Object> map = this.goodsService.loadData(id);
        model.addAllAttributes(map);

        this.goodsHtmlService.create(id);
        return "item";
    }
}
