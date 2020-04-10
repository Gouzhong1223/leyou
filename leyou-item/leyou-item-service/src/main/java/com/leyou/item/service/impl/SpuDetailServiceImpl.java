package com.leyou.item.service.impl;

import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.SpuDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-18 22:40
 * @email : 1162864960@qq.com
 */
@Service
@Transactional
public class SpuDetailServiceImpl implements SpuDetailService {

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Override
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }
}
