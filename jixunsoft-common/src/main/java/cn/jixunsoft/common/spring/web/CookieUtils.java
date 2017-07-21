package cn.jixunsoft.common.spring.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public static Cookie getCookie(HttpServletRequest request, String key) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String key, String value) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    cookie.setValue(value);
                    addCookie(response, cookie);
                    return;
                }
            }
        }

        Cookie cookie = new Cookie(key.trim(), value.trim());
        addCookie(response, cookie);
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String key, String value, int maxAge) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    cookie.setValue(value);
                    cookie.setMaxAge(maxAge);
                    addCookie(response, cookie);
                    return;
                }
            }
        }

        Cookie cookie = new Cookie(key.trim(), value.trim());
        cookie.setMaxAge(maxAge);
        addCookie(response, cookie);
    }

    public static void addCookie(HttpServletResponse response, Cookie cookie) {
        if (cookie != null) {
            response.addCookie(cookie);
        }
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String key) {
        deleteCookie(response, getCookie(request, key));
    }

    public static void deleteCookie(HttpServletResponse response, Cookie cookie) {

        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            response.addCookie(cookie);
        }
    }
}
