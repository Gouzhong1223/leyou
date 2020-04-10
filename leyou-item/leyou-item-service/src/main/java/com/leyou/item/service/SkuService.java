package com.leyou.item.service;

import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;

import java.util.List;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-19 17:02
 * @email : 1162864960@qq.com
 */
public interface SkuService {
    void saveGoods(SpuBo spuBo);

    List<Sku> querySkusBySpuId(Long spuId);

    void updateGoods(SpuBo spuBo);
}
