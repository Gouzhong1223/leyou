package com.leyou.elasticsearch.test;

import com.leyou.client.GoodsClient;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Spu;
import com.leyou.pojo.Goods;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-21 14:50
 * @email : 1162864960@qq.com
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTeat {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsClient goodsClient;

    @Test
    public void createIndex(){
        Integer page = 1;
        Integer rows = 100;

        do {
            // 分批查询spuBo
            PageResult<SpuBo> pageResult = this.goodsClient.querySpuByPage(null, true, page, rows);
            // 遍历spubo集合转化为List<Goods>
            List<Goods> goodsList = pageResult.getItems().stream().map(spuBo -> {
                try {
                    return this.searchService.buildGoods((Spu) spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            this.goodsRepository.saveAll(goodsList);

            // 获取当前页的数据条数，如果是最后一页，没有100条
            rows = pageResult.getItems().size();
            // 每次循环页码加1
            page++;
            System.out.println(rows);
        } while (rows == 100);
    }
}
/*
var vm = new Vue({
        el: "#searchApp",
        data: {
            ly,
            search: {
                key: "",
                page: 1,
                filter: {}
            },
            goodsList: [],
            total: 0,
            totalPage: 1,
            filters: [],
            show: false
        },
        created(){
            // 如果搜索条件为null，直接返回
            if (!location.search) {
                return;
            }
            // 转化成search对象{key: value}
            const search = ly.parse(location.search.substring(1));
            // 初始化page参数，如果地址栏有page参数，获取page参数，并把page转化为int类型
            search.page = parseInt(search.page) || 1;
            search.filter = ly.parse(search.filter) || {};

            this.search = search;

            this.loadData();
        },
        watch: {
            search: {
                deep: true,
                handler(newVal, oldVal){
                    if (!oldVal||!oldVal.key) {
                        return ;
                    }
                    window.location = "http://www.leyou.com/search.html?" + ly.stringify(this.search);
                }
            }
        },
        methods: {
            loadData(){
                ly.http.post("/search/page", this.search).then(({data}) => {

                    data.items.forEach(goods => {
                        goods.skus = JSON.parse(goods.skus);
                        goods.selected = goods.skus[0];
                    })
                    this.goodsList = data.items;
                    // 获取总页数
                    this.totalPage = data.totalPage;
                    // 获取总条数
                    this.total = data.total;
                    // 分类添加到过滤集合：filters
                    this.filters.push({
                        k: "分类",
                        options: data.categories
                    });
                    // 品牌添加到过滤集合：filters
                    this.filters.push({
                        k: "品牌",
                        options: data.brands
                    });
                    data.specs.forEach(spec => {
                        // 把字符串数组转化成对象数组{name:option}
                        spec.options = spec.options.map(option => ({name: option}));
                        this.filters.push(spec);
                    })
                }).catch(()=>{

                });
            },
            index(i){
                if (this.search.page <= 3 || this.totalPage <= 5) {
                    return i;
                } else if(this.search.page >= this.totalPage - 2) {
                    return this.totalPage - 5 + i;
                } else {
                    return this.search.page - 3 + i;
                }
            },
            pre(){
                if (this.search.page > 1) {
                    this.search.page--;
                }
            },
            next(){
                if (this.search.page < this.totalPage) {
                    this.search.page++;
                }
            },
            selectFilter(k, o){
                // 定义一个空对象，统一接收过滤条件
                const obj = {};
                // 把已有值copy给obj对象
                Object.assign(obj, this.search.filter);
                if (k == "分类" || k == "品牌"){
                    obj[k] = o.id;
                } else {
                    obj[k] = o.name;
                }

                this.search.filter = obj;
            }
        },
        components:{
            lyTop: () => import("./js/pages/top.js")
        }
    });
 */