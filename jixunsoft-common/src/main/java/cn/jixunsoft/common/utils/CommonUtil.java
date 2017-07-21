package cn.jixunsoft.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cn.jixunsoft.common.base.ResponseCode;
import cn.jixunsoft.common.exception.BusinessException;

/**
 * 通用工具类
 * 
 * @author zhuww
 * 
 * @date 2013-9-4
 * 
 */
public class CommonUtil {
    
    /**
     * 将字符串按分隔符分割并转成整型列表
     * 
     * @param ids
     * @param separator
     * @return
     */
    public static List<Integer> str2IntList(String ids, String separator) {
        List<Integer> idList = new ArrayList<Integer>();
        if (StringUtils.isBlank(ids)) {
            return idList;
        }

        String[] arrId = ids.split("\\" + separator);
        for (String id : arrId) {
            idList.add(Integer.parseInt(id));
        }
        return idList;
    }

    /**
     * 拷贝bean对象属性到新创建的bean对象 支持一层浅拷贝
     */
    public static <T> T copyProperties(Class<T> clazz, Object orig) {
        if (null == clazz || null == orig) {
            return null;
        }

        T dest = null;

        try {
            dest = clazz.newInstance();
            BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.INTERNAL_ERROR, "copy properties failed." + e.getMessage());
        }

        return dest;
    }

    /**
     * 拷贝bean对象属性到已有bean对象 支持一层浅拷贝
     */
    public static void copyProperties(Object dest, Object orig) {
        if (null == dest || null == orig) {
            return;
        }

        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.INTERNAL_ERROR, "copy properties failed." + e.getMessage());
        }

        return;
    }

    /**
     * 获取子列表
     * 
     * @param list
     * @param start
     * @param limit
     * @return
     */
    public static <T> List<T> getSubList(List<T> list, int start, int limit) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<T>(0);
        }

        int listLength = list.size();
        if (start > listLength - 1) {
            return new ArrayList<T>();
        }

        limit = start + limit > listLength ? listLength - start : limit;
        return list.subList(start, start + limit);
    }

}
