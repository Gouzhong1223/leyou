package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecGroupService;
import com.leyou.item.service.SpecParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date : create by QingSong in 2019-08-18 12:32
 * @email : 1162864960@qq.com
 */
@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecGroupService specGroupService;

    @Autowired
    private SpecParamService specParamService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecificationController.class);

    /**
     * 根据cid查询分组
     *
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroupsByCid(@PathVariable("cid") Long cid) {

        List<SpecGroup> specGroupList = this.specGroupService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty(specGroupList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroupList);

    }

    /**
     * 根据条件查询规格参数
     *
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    ) {
        List<SpecParam> specParamList = this.specParamService.queryParams(gid, cid, generic, searching);
        if (CollectionUtils.isEmpty(specParamList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specParamList);

    }

    /**
     * 新增分组
     *
     * @param specGroup
     * @return
     */
    @PostMapping("group")
    @ResponseBody
    public ResponseEntity<Void> addSpecGroupByCid(@RequestBody SpecGroup specGroup) {
        if (StringUtils.isEmpty(specGroup)) {
            LOGGER.info("请求参数有误 : {}", specGroup);
        }
        this.specGroupService.addSpecGroup(specGroup);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除分组
     *
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteGroupById(@PathVariable("id") Long id) {
        if (StringUtils.isEmpty(id)) {
            LOGGER.info("请求参数有误 : {}", id);
            return ResponseEntity.badRequest().build();
        }
        this.specGroupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 更改分组
     *
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    @ResponseBody
    public ResponseEntity<Void> updateGroup(@RequestBody SpecGroup specGroup) {
        if (StringUtils.isEmpty(specGroup)) {
            LOGGER.info("请求参数有误 : {}", specGroup);
            return ResponseEntity.badRequest().build();
        }
        this.specGroupService.updateGroup(specGroup);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增参数
     *
     * @param specParam
     * @return
     */
    @PostMapping("param")
    @ResponseBody
    public ResponseEntity<Void> addParams(@RequestBody SpecParam specParam) {
        if (StringUtils.isEmpty(specParam)) {
            LOGGER.info("新增失败！ : {}", specParam);
            return ResponseEntity.badRequest().build();
        }
        this.specParamService.addParams(specParam);
        return ResponseEntity.ok().build();
    }

    /**
     * 更改参数
     *
     * @param specParam
     * @return
     */
    @PutMapping("param")
    @ResponseBody
    public ResponseEntity<Void> updateParams(@RequestBody SpecParam specParam) {
        if (StringUtils.isEmpty(specParam)) {
            LOGGER.info("修改失败！ : {}", specParam);
            return ResponseEntity.badRequest().build();
        }
        this.specParamService.updateParam(specParam);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除参数
     *
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteParam(@PathVariable Long id) {
        if (StringUtils.isEmpty(id)) {
            LOGGER.info("删除失败！ : {}", id);
            return ResponseEntity.badRequest().build();
        }
        this.specParamService.deleteParam(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据分组id查询分组以及参数
     *
     * @param cid
     * @return
     */
    @GetMapping("group/param/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsWithParam(@PathVariable("cid") Long cid) {
        List<SpecGroup> specGroupList = this.specGroupService.queryGroupsWithParam(cid);
        if (CollectionUtils.isEmpty(specGroupList)) {
            LOGGER.info("未查询到 : {}", cid);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroupList);
    }

}
