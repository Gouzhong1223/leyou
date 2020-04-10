package com.leyou.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecificationClient;
import com.leyou.item.pojo.*;
import com.leyou.pojo.Goods;
import com.leyou.pojo.SearchRequest;
import com.leyou.pojo.SearchResult;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.SearchService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-21 13:41
 * @email : 1162864960@qq.com
 */
@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private GoodsRepository goodsRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Goods buildGoods(Spu spu) throws IOException {
        Goods goods = new Goods();

        //根据分类id查询分类名称
        List<String> namesBuId = this.categoryClient.queryNamesBuId(Arrays.asList(spu.getCid2(), spu.getCid2(), spu.getCid3()));

        //查询品牌
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());

        //根据spuid查询所有sku
        List<Sku> skus = this.goodsClient.querySkusBySpuId(spu.getId());

        //初始化价格集合
        ArrayList<Long> prices = new ArrayList<>();

        //手机sku必要的字段信息
        List<Map<String, Object>> skuMapList = new ArrayList<>();
        skus.forEach(sku -> {
            prices.add(sku.getPrice());

            Map<String, Object> map = new HashMap<>();

            map.put("id", sku.getId());
            map.put("title", sku.getTitle());
            map.put("price", sku.getPrice());
            //获取sku中的图片，数据库中的图片可能是多张图片，多张图片以逗号分隔,返回图片数组，获取第一张图片
            map.put("image", StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);

            skuMapList.add(map);
        });

        //根据spu中的cid3查询所有搜索的规格参数
        List<SpecParam> specParamList = this.specificationClient.queryParams(null, spu.getCid3(), null, true);

        //根据spuid查询spudetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spu.getId());

        // 获取通用的规格参数
        Map<Long, Object> genericSpecMap = MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>() {
        });
        // 获取特殊的规格参数
        Map<Long, List<Object>> specialSpecMap = MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<Object>>>() {
        });

        // 定义map接收{规格参数名，规格参数值}
        Map<String, Object> paramMap = new HashMap<>();
        specParamList.forEach(param -> {
            // 判断是否通用规格参数
            if (param.getGeneric()) {
                // 获取通用规格参数值
                String value = genericSpecMap.get(param.getId()).toString();
                // 判断是否是数值类型
                if (param.getNumeric()) {
                    // 如果是数值的话，判断该数值落在那个区间
                    value = chooseSegment(value, param);
                }
                // 把参数名和值放入结果集中
                paramMap.put(param.getName(), value);
            } else {
                paramMap.put(param.getName(), specialSpecMap.get(param.getId()));
            }
        });


        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setBrandId(spu.getBrandId());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        goods.setAll(spu.getTitle() + " " + StringUtils.join(namesBuId, " ") + " " + brand.getName());
        //获取spu价格
        goods.setPrice(prices);
        //获取spu下的所有sku，并转化成json数据
        goods.setSkus(MAPPER.writeValueAsString(skuMapList));
        //获取所有查询的规格参数
        goods.setSpecs(paramMap);
        return goods;
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) {

        if (StringUtils.isBlank(searchRequest.getKey())) {
            return null;
        }
        //QueryBuilder basicQuery = QueryBuilders.matchQuery("all", searchRequest.getKey()).operator(Operator.AND);
        BoolQueryBuilder basicQuery = buildBoolQueryBuilder(searchRequest);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(basicQuery);

        queryBuilder.withPageable(PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize()));

        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle"}, null));

        String categoryAggName = "categorys";
        String brandAggName = "brands";

        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());

        //获取局和结果集
        List<Map<String, Object>> categories = getCategoryAggResult(goodsPage.getAggregation(categoryAggName));
        List<Brand> brands = getBrandAggResult(goodsPage.getAggregation(brandAggName));

        List<Map<String, Object>> specs = null;
        //判断是否是一个分类，只有是一个分类是，才做规格参数的聚合
        if (!CollectionUtils.isEmpty(categories) && categories.size() == 1) {
            //对规格参数进行聚合
            specs = getParamAggResult((Long) categories.get(0).get("id"), basicQuery);

        }


        return new SearchResult(goodsPage.getTotalElements(), goodsPage.getTotalPages(), goodsPage.getContent(), categories, brands, specs);
    }

    @Override
    public void save(Long id) throws IOException {
        Spu spu = this.goodsClient.querySpuById(id);
        Goods goods = this.buildGoods(spu);
        this.goodsRepository.save(goods);
    }

    /**
     * 构建布尔查询
     *
     * @param searchRequest
     * @return
     */
    private BoolQueryBuilder buildBoolQueryBuilder(SearchRequest searchRequest) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //添加基本条件查询
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", searchRequest.getKey()).operator(Operator.AND));
        //添加过滤条件
        Map<String, Object> filter = searchRequest.getFilter();
        for (Map.Entry<String, Object> stringObjectEntry : filter.entrySet()) {
            String key = stringObjectEntry.getKey();
            if (StringUtils.equals("品牌", key)) {
                key = "brandId";
            } else if (StringUtils.equals("分类", key)) {
                key = "cid3";
            } else {
                key = "specs." + key + ".keyword";
            }
            boolQueryBuilder.filter(QueryBuilders.termQuery(key, stringObjectEntry.getValue()));
        }
        return boolQueryBuilder;
    }

    /**
     * 根据查询条件聚合规格参数
     *
     * @param cid
     * @param basicQuery
     * @return
     */
    private List<Map<String, Object>> getParamAggResult(Long cid, QueryBuilder basicQuery) {

        //自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //添加基本查询条件
        queryBuilder.withQuery(basicQuery);

        //查询需要聚合的规格参数
        List<SpecParam> params = this.specificationClient.queryParams(null, cid, null, true);

        //添加规格参数聚合
        params.forEach(param -> {
            queryBuilder.addAggregation(AggregationBuilders.terms(param.getName()).field("specs." + param.getName() + ".keyword"));
        });

        //添加结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));

        //执行查询
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());

        //解析聚合结果集,key->聚合名称(规格参数名)
        List<Map<String, Object>> spec = new ArrayList<>();

        Map<String, Aggregation> aggregationMap = goodsPage.getAggregations().asMap();
        for (Map.Entry<String, Aggregation> stringAggregationEntry : aggregationMap.entrySet()) {
            //初始化一个Map{k:规格参数名,options:聚合规格参数值}
            Map<String, Object> map = new HashMap<>();
            map.put("k", stringAggregationEntry.getKey());

            //初始化一个options集合，搜集桶中的key
            List<String> options = new ArrayList<>();

            //获取聚合
            StringTerms terms = (StringTerms) stringAggregationEntry.getValue();

            terms.getBuckets().forEach(bucket -> {
                options.add(bucket.getKeyAsString());
            });
            map.put("options", options);

            spec.add(map);
        }

        return spec;
    }

    /**
     * 解析品牌的局和结果集
     *
     * @param aggregation
     * @return
     */
    private List<Brand> getBrandAggResult(Aggregation aggregation) {

        LongTerms terms = (LongTerms) aggregation;

        //获取聚合桶
        return terms.getBuckets().stream().map(bucket -> {
            return this.brandClient.queryBrandById(bucket.getKeyAsNumber().longValue());
        }).collect(Collectors.toList());

        /*
        List<Brand> brands = new ArrayList<>();
        //获取聚合桶
        terms.getBuckets().forEach(bucket -> {
            Brand brand = this.brandClient.queryBrandById(bucket.getKeyAsNumber().longValue());
            brands.add(brand);
        });
        return brands;
        */
    }

    /**
     * 解析分类的聚合结果集
     *
     * @param aggregation
     * @return
     */
    private List<Map<String, Object>> getCategoryAggResult(Aggregation aggregation) {

        LongTerms terms = (LongTerms) aggregation;
        return terms.getBuckets().stream().map(bucket -> {
            Map<String, Object> map = new HashMap<>();
            Long id = bucket.getKeyAsNumber().longValue();
            List<String> names = this.categoryClient.queryNamesBuId(Arrays.asList(id));
            map.put("id", id);
            map.put("name", names.get(0));
            return map;
        }).collect(Collectors.toList());
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + p.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + p.getUnit() + "以下";
                } else {
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }
}
