package com.leyou.service.impl;

import com.leyou.service.GoodsHtmlService;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-23 18:34
 * @email : 1162864960@qq.com
 */
@Service
public class GoodsHtmlServiceImpl implements GoodsHtmlService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private GoodsService goodsService;


    @Override
    public void create(Long spuId) {

        //初始化运行上下文
        Context context = new Context();
        //设置数据模型
        context.setVariables(this.goodsService.loadData(spuId));
        PrintWriter printWriter = null;
        try {
            File file = new File("D:\\nginx-1.12.2\\html\\item\\" + spuId + ".html");

            printWriter = new PrintWriter(file);

            this.templateEngine.process("item", context, printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
