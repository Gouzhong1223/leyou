package com.leyou.service.impl;

import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecificationClient;
import com.leyou.item.pojo.*;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-22 22:18
 * @email : 1162864960@qq.com
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;


    @Override
    public Map<String, Object> loadData(Long spuId) {
        Map<String, Object> model = new HashMap<>();

        //查询spu,根据spuid
        Spu spu = this.goodsClient.querySpuById(spuId);

        //查询spuDetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spuId);

        //查询categories
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> namesBuId = this.categoryClient.queryNamesBuId(cids);

        //初始化一个分类的Map
        List<Map<String, Object>> categories = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cids.get(i));
            map.put("name", namesBuId.get(i));
            categories.add(map);
        }

        //查询品牌
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());

        //查询skus
        List<Sku> skus = this.goodsClient.querySkusBySpuId(spuId);

        //查询groups
        List<SpecGroup> groups = this.specificationClient.queryGroupsWithParam(spu.getCid3());

        //查询paramsMap
        List<SpecParam> params = this.specificationClient.queryParams(null, spu.getCid3(), false, null);
        //初始化特殊规格参数Map
        Map<Long, String> paramMap = new HashMap<>();
        params.forEach(param -> {
            paramMap.put(param.getId(),param.getName());
        });
        model.put("spu", spu);
        model.put("spuDetail", spuDetail);
        model.put("categories", categories);
        model.put("brand", brand);
        model.put("skus", skus);
        model.put("groups", groups);
        model.put("paramMap", paramMap);


        return model;
    }
}
