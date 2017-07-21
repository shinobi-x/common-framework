package cn.jixunsoft.common.page;


/**
 * 页码DTO
 * 
 * @author zhuww
 *
 */
public class PageUtil {
    
    /**
     * 获取分页
     * 
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static int[] getPager(Integer pageNo, Integer pageSize) {
        // 默认20条一页
        pageSize = (pageSize == null || pageSize <= 0) ? 20 : pageSize;

        // 默认页码
        pageNo = (pageNo == null || pageNo <= 0) ? 1 : pageNo;

        // 计算开始位置
        Integer start = (pageNo - 1) * pageSize;

        return new int[] { start, pageSize };
    }

}
