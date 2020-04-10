package com.leyou.item.service.impl;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecGroupService;
import com.leyou.item.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-18 12:28
 * @email : 1162864960@qq.com
 */
@Service
@Transactional
public class SpecGroupServiceImpl implements SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamService specParamService;

    /**
     * 根据id查询分组
     *
     * @param cid
     * @return
     */
    @Override
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.specGroupMapper.select(specGroup);
    }

    /**
     * 新增分组
     *
     * @param specGroup
     */
    @Override
    public void addSpecGroup(SpecGroup specGroup) {
        SpecGroup group = new SpecGroup();
        group.setCid(specGroup.getCid());
        group.setName(specGroup.getName());
        this.specGroupMapper.insertSelective(group);
    }

    /**
     * 删除分组
     *
     * @param id
     */
    @Override
    public void deleteGroup(Long id) {
        this.specGroupMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新分组
     *
     * @param specGroup
     */
    @Override
    public void updateGroup(SpecGroup specGroup) {
        SpecGroup group = this.specGroupMapper.selectByPrimaryKey(specGroup.getId());
        group.setName(specGroup.getName());
        this.specGroupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public List<SpecGroup> queryGroupsWithParam(Long cid) {
        List<SpecGroup> groups = this.queryGroupsByCid(cid);
        groups.forEach(group ->{
            List<SpecParam> params = this.specParamService.queryParams(group.getId(), null, null, null);
            group.setParams(params);
        });
        return groups;
    }


}
