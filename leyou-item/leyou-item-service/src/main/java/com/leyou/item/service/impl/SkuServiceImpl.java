package com.leyou.item.service.impl;

import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.pojo.Stock;
import com.leyou.item.service.SkuService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-19 17:03
 * @email : 1162864960@qq.com
 */
@Service
@Transactional
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void saveGoods(SpuBo spuBo) {
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);
        SaveSkuAndStock(spuBo);
        sendMsg("insert",spuBo.getId());
    }

    private void SaveSkuAndStock(SpuBo spuBo) {
        for (Sku skus : spuBo.getSkus()) {
            skus.setId(null);
            skus.setSpuId(spuBo.getId());
            skus.setCreateTime(new Date());
            skus.setLastUpdateTime(skus.getCreateTime());
            this.skuMapper.insertSelective(skus);
            Stock stock = new Stock();
            stock.setSkuId(skus.getId());
            stock.setStock(skus.getStock());
            this.stockMapper.insertSelective(stock);
            sendMsg("insert",spuBo.getId());
        }
    }

    @Override
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(sku);
        for (Sku skus1 : skus) {
            Stock stock = this.stockMapper.selectByPrimaryKey(skus1.getId());
            skus1.setStock(stock.getStock());
        }
        return skus;
    }

    @Override
    public void updateGoods(SpuBo spuBo) {
        //查询需要删除的sku
        Sku sku = new Sku();
        sku.setSpuId(spuBo.getId());
        List<Sku> skus = this.skuMapper.select(sku);
        for (Sku skus1 : skus) {
            //删除stock
            this.stockMapper.deleteByPrimaryKey(skus1.getId());
        }
        //删除sku
        Sku sku1 = new Sku();
        sku1.setSpuId(spuBo.getId());
        this.skuMapper.delete(sku1);
        //新增sku
        //新增stock
        this.SaveSkuAndStock(spuBo);
        //更新spu
        spuBo.setCreateTime(null);
        spuBo.setLastUpdateTime(new Date());
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spuBo);
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
        sendMsg("update",spuBo.getId());

    }

    private void sendMsg(String type, Long id) {
        //发送队列消息
        try {
            this.amqpTemplate.convertAndSend("item." + type,id);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
    }
}
