package com.leyou.item.service.impl;

import com.leyou.item.mapper.StockMapper;
import com.leyou.item.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-19 17:02
 * @email : 1162864960@qq.com
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;
}
