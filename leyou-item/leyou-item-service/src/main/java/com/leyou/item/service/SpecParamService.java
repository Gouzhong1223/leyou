package com.leyou.item.service;

import com.leyou.item.pojo.SpecParam;

import java.util.List;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-18 12:27
 * @email : 1162864960@qq.com
 */
public interface SpecParamService {

    void addParams(SpecParam specParam);

    void updateParam(SpecParam specParam);

    void deleteParam(Long id);

    List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching);
}
