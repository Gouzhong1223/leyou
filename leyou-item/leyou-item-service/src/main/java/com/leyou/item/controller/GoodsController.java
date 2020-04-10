package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.SkuService;
import com.leyou.item.service.SpuDetailService;
import com.leyou.item.service.SpuService;
import com.leyou.item.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-18 22:42
 * @email : 1162864960@qq.com
 */
@Controller
public class GoodsController {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SpuDetailService spuDetailService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private StockService stockService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);

    /**
     * 根据条件分页查询Spu
     *
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ) {

        PageResult<SpuBo> spuBoPageResult = this.spuService.querySpuByPage(key, saleable, page, rows);
        if (CollectionUtils.isEmpty(spuBoPageResult.getItems()) || spuBoPageResult == null) {
            LOGGER.info("没有查到数据！ : {}", spuBoPageResult);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuBoPageResult);

    }

    /**
     * 新增商品
     *
     * @param spuBo
     * @return
     */
    @PostMapping("goods")
    @ResponseBody
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo) {
        this.skuService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据spuid查询spudetail
     *
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long spuId) {
        SpuDetail spuDetails = this.spuDetailService.querySpuDetailBySpuId(spuId);
        if (spuDetails == null) {
            LOGGER.info("未查询到 : {}", spuDetails);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuDetails);
    }

    /**
     * 根据spuid查询spu集合
     *
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkusBySpuId(@RequestParam("id") Long spuId) {
        List<Sku> skus = this.skuService.querySkusBySpuId(spuId);
        if (CollectionUtils.isEmpty(skus)) {
            LOGGER.info("未查询到 : {}", spuId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }

    /**
     * 修改商品
     *
     * @param spuBo
     * @return
     */
    @PutMapping("goods")
    @ResponseBody
    public ResponseEntity<Void> upadteGoods(@RequestBody SpuBo spuBo) {
        if (StringUtils.isEmpty(spuBo)) {
            LOGGER.info("请求参数有误 : {}", spuBo);
            return ResponseEntity.badRequest().build();
        }
        this.skuService.updateGoods(spuBo);
        return ResponseEntity.noContent().build();

    }

    /**
     * 根据id查询Spu
     *
     * @param id
     * @return
     */
    @GetMapping("{spuId}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("spuId") Long id) {
        Spu spu = this.spuService.querySpuById(id);
        if (StringUtils.isEmpty(spu)) {
            LOGGER.info("未查询到 : {}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spu);
    }
}
