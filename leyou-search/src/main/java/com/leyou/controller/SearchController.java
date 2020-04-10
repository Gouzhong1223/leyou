package com.leyou.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.pojo.Goods;
import com.leyou.pojo.SearchRequest;
import com.leyou.pojo.SearchResult;
import com.leyou.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-21 16:41
 * @email : 1162864960@qq.com
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @PostMapping("page")
    public ResponseEntity<SearchResult> search(@RequestBody SearchRequest searchRequest){
        SearchResult goodsPageResult = this.searchService.search(searchRequest);
        if (CollectionUtils.isEmpty(goodsPageResult.getItems()) || goodsPageResult == null) {
            LOGGER.info("没有查询到 : {}",searchRequest);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goodsPageResult);

    }
}
