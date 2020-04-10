package com.leyou.common.pojo;


import java.util.List;

/**
 * @author Gouzhong
 * @Description : 分页结果集
 * @date Create By Qingsong in 14:20 2019/8/16
 * @email : 1162864960@qq.com
 */
public class PageResult<T> {

    private Long total;
    private Integer totalpage;
    private List<T> items;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(Integer totalpage) {
        this.totalpage = totalpage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", totalpage=" + totalpage +
                ", items=" + items +
                '}';
    }

    public PageResult(Long total, Integer totalpage, List<T> items) {
        this.total = total;
        this.totalpage = totalpage;
        this.items = items;
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult() {
    }
}
