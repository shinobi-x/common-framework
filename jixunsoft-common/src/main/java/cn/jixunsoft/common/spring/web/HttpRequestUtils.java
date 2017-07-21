package cn.jixunsoft.common.spring.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class HttpRequestUtils {

    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("X-Real-IP");

        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("X-Forwarded-For");

        if (StringUtils.isNotBlank(ip) && StringUtils.equalsIgnoreCase(ip, "unknown")) {
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            }
            return ip;
        }

        return request.getRemoteAddr();
    }
}
