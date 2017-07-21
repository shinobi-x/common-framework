package cn.jixunsoft.cache.utils;

import org.apache.commons.lang.StringUtils;


public class CacheUtils {

    /**
     * 产生缓存Key
     * 
     * @param keyPrefix
     * @param combins
     * @return
     */
    public static String generateCacheKey(String keyPrefix, Object... combins) {

        if (StringUtils.isBlank(keyPrefix)) {
            throw new IllegalArgumentException("keyPrefix is null");
        }

        StringBuilder sb = new StringBuilder(keyPrefix);
        for (int i = 0; i < combins.length; i++) {
            Object objectCombin = combins[i];
            if (objectCombin != null) {
                sb.append("_").append(objectCombin.toString());
            }
        }

        return sb.toString();
    }
}
