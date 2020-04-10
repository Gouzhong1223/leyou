package com.leyou.item.pojo;

import javax.persistence.*;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-18 12:22
 * @email : 1162864960@qq.com
 */
@Table(name = "tb_spec_param")
public class SpecParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNumeric() {
        return numeric;
    }

    public void setNumeric(Boolean numeric) {
        this.numeric = numeric;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getGeneric() {
        return generic;
    }

    public void setGeneric(Boolean generic) {
        this.generic = generic;
    }

    public Boolean getSearching() {
        return searching;
    }

    public void setSearching(Boolean searching) {
        this.searching = searching;
    }

    public String getSegments() {
        return segments;
    }

    public void setSegments(String segments) {
        this.segments = segments;
    }

    public SpecParam(Long cid, Long groupId, String name, Boolean numeric, String unit, Boolean generic, Boolean searching, String segments) {
        this.cid = cid;
        this.groupId = groupId;
        this.name = name;
        this.numeric = numeric;
        this.unit = unit;
        this.generic = generic;
        this.searching = searching;
        this.segments = segments;
    }

    public SpecParam() {
    }

    @Override
    public String toString() {
        return "SpecParam{" +
                "id=" + id +
                ", cid=" + cid +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", numeric=" + numeric +
                ", unit='" + unit + '\'' +
                ", generic=" + generic +
                ", searching=" + searching +
                ", segments='" + segments + '\'' +
                '}';
    }
}
