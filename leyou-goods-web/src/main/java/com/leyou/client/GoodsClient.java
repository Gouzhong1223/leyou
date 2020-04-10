package com.leyou.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-21 12:50
 * @email : 1162864960@qq.com
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
