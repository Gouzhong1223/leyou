package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;

import java.util.List;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-18 12:28
 * @email : 1162864960@qq.com
 */
public interface SpecGroupService {
    List<SpecGroup> queryGroupsByCid(Long cid);

    void addSpecGroup(SpecGroup specGroup);

    void deleteGroup(Long id);

    void updateGroup(SpecGroup specGroup);

    List<SpecGroup> queryGroupsWithParam(Long cid);
}
