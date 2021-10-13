package com.security.login.result;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * Created by zhong on 2018/6/11.
 */
public class PageResult<T> extends ListResult<T> {

    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 每页多少条
     */
    private Integer pageSize;
    /**
     * 当前页
     */
    private Integer pageIndex;
    /**
     * 数据总个数
     */
    private Integer totalCount;

    public PageResult() {

    }

    public PageResult(Integer pageIndex, Integer pageSize, Integer totalCount, List<T> data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        computeTotalPage();
        setData(data);
        setResult(SUCCESS_CODE);
    }

    private void computeTotalPage() {
        if (pageSize <= 0 || totalCount == 0) {
            totalPage = 0;
            return;
        }
        long m = totalCount % pageSize;
        totalPage = (totalCount / pageSize);
        if (m > 0) {
            totalPage++;
        }
    }

    public static <K extends Object> PageResult<K> success(Integer pageIndex, Integer pageSize, Integer totalCount,
            List<K> data) {
        return new PageResult<>(pageIndex, pageSize, totalCount, data);
    }

    public static <K> PageResult<K> success(IPage<K> page, List<K> data) {
        return new PageResult<>((int) page.getCurrent(), (int) page.getSize(), (int) page.getTotal(), data);
    }

    public static <K extends Object> PageResult<K> success(com.github.pagehelper.Page<K> page, List<K> data) {
        return new PageResult<>(page.getPageNum(), page.getPageSize(), (int) page.getTotal(), data);
    }

    public static <T> PageResult<T> failPageResult(String message) {
        PageResult<T> result = new PageResult<>();
        result.setResult(FAIL_CODE);
        result.setMessage(message);
        return result;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
