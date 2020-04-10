package com.leyou.item.pojo;

import javax.persistence.*;
import java.util.List;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-18 12:21
 * @email : 1162864960@qq.com
 */
@Table(name = "tb_spec_group")
public class SpecGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cid;

    private String name;

    @Transient
    private List<SpecParam> params;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecParam> getParams() {
        return params;
    }

    public void setParams(List<SpecParam> params) {
        this.params = params;
    }

    public SpecGroup(Long cid, String name, List<SpecParam> params) {
        this.cid = cid;
        this.name = name;
        this.params = params;
    }

    public SpecGroup() {
    }

    @Override
    public String toString() {
        return "SpecGroup{" +
                "id=" + id +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                ", params=" + params +
                '}';
    }

    public SpecGroup(Long cid, String name) {
        this.cid = cid;
        this.name = name;
    }
}
