package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Spu;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-18 22:38
 * @email : 1162864960@qq.com
 */
public interface SpuService {

    PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows);

    Spu querySpuById(Long id);
}
