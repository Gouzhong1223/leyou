package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Gouzhong
 * @Description :
 * @date Create By Qingsong in 21:06 2019/8/15
 * @email : 1162864960@qq.com
 */
public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {
}
