package cn.jixunsoft.common.page;


/**
 * 页码DTO
 * 
 * @author zhuww
 *
 */
public class PageDto {
    
    public static final int PAGE_SIZE = 30; // 每页记录数
    
    public PageDto(Integer curPage, Integer totalCount, Integer pageSize) {
        super();
        this.totalCount = (totalCount == null || totalCount < 0 ? 0:totalCount);
        this.pageSize = (pageSize == null || pageSize < 1 ? PAGE_SIZE:pageSize);
        int totalPage = ((this.totalCount + this.pageSize - 1) / this.pageSize);
        this.totalPage = (totalPage < 1 ? 1:totalPage);
        curPage = (curPage == null || curPage < 1 ? 1:curPage);
        this.curPage = (curPage > this.totalPage ? this.totalPage:curPage);
    }

    /**
     * 当前页码
     */
    private Integer curPage;
    
    /**
     * 总记录数
     */
    private Integer totalCount;
    
    /**
     * 每页记录数
     */
    private Integer pageSize;
    
    /**
     * 总页码
     */
    private Integer totalPage;

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}
