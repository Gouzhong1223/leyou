package com.leyou.item.service.impl;

import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-18 12:29
 * @email : 1162864960@qq.com
 */
@Service
@Transactional
public class SpecParamServiceImpl implements SpecParamService {

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据条件查询规格参数
     *
     * @param gid
     * @return
     */
    @Override
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setGeneric(generic);
        specParam.setSearching(searching);
        return this.specParamMapper.select(specParam);
    }

    /**
     * 新增参数
     * @param specParam
     */
    @Override
    public void addParams(SpecParam specParam) {
        this.specParamMapper.insertSelective(specParam);
    }

    /**
     * 更改参数
     * @param specParam
     */
    @Override
    public void updateParam(SpecParam specParam) {
        this.specParamMapper.updateByPrimaryKeySelective(specParam);
    }

    /**
     * 删除参数
     * @param id
     */
    @Override
    public void deleteParam(Long id) {
        this.specParamMapper.deleteByPrimaryKey(id);
    }

}
