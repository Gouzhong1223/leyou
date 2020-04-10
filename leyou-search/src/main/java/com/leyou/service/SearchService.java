package com.leyou.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.pojo.Goods;
import com.leyou.pojo.SearchRequest;
import com.leyou.pojo.SearchResult;

import java.io.IOException;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-21 13:40
 * @email : 1162864960@qq.com
 */
public interface SearchService {

    public Goods buildGoods(Spu spu) throws IOException;

    SearchResult search(SearchRequest searchRequest);

    void save(Long id) throws IOException;
}
