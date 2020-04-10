package com.leyou.repository;

import com.leyou.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-21 14:52
 * @email : 1162864960@qq.com
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
