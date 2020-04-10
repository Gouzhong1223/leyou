package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Gouzhong
 * @Description :
 * @date Create By Qingsong in 15:00 2019/8/16
 * @email : 1162864960@qq.com
 */
public interface BrandMapper extends Mapper<Brand> {
    /**
     * 新增cid
     * @param cid
     * @param id
     */
    @Insert("INSERT INTO tb_category_brand (category_id,brand_id) VALUES (#{cid},#{bid})")
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long id);


    @Select("SELECT * FROM tb_brand a INNER JOIN tb_category_brand b ON a.`id`=b.`brand_id` WHERE b.`category_id` = #{cid} ")
    List<Brand> selectBrandsByCid(Long cid);
}
