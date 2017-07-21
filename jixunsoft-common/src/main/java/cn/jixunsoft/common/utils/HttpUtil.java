package cn.jixunsoft.common.utils;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpUtil {

    public static final Logger logger = Logger.getLogger(HttpUtil.class);

    public static String postSimpleRequestContent(CloseableHttpClient httpClient, String url, Map<String, Object> params) {

        try {
            HttpPost httpPost = new HttpPost(url);
            JsonObject jsonParam = new JsonObject();
            if (MapUtils.isNotEmpty(params)) {
                for (Entry<String, Object> entry : params.entrySet()) {
                    jsonParam.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
            StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            logger.error("获取接口失败", e);
            throw new RuntimeException(e);
        }
    }

    public static JsonObject postSimpleRequestJson(CloseableHttpClient httpClient, String url, Map<String, Object> params) {

        try {
            String content = postSimpleRequestContent(httpClient, url, params);
            return new JsonParser().parse(content).getAsJsonObject();
        } catch (Exception e) {
            logger.error("获取接口失败", e);
            throw new RuntimeException(e);
        }
    }

    public static String getSimpleRequestContent(CloseableHttpClient httpClient, String url, Map<String, Object> params) {
        String paramUrl = null;
        if (MapUtils.isNotEmpty(params)) {
            StringBuffer paramSb = new StringBuffer();
            for (Entry<String, Object> entry : params.entrySet()) {
                paramSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            paramUrl = paramSb.toString();
            if (StringUtils.endsWith(paramUrl, "&")) {
                paramUrl = StringUtils.removeEnd(paramUrl, "&");
            }
        }

        String requestUrl = url;

        if (StringUtils.isNotEmpty(paramUrl)) {
            if (StringUtils.contains(requestUrl, "?")) {
                requestUrl = requestUrl + "&" + paramUrl;
            } else {
                requestUrl = requestUrl + "?" + paramUrl;
            }
        }

        HttpGet httpGet = new HttpGet(requestUrl);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            logger.error("获取接口失败", e);
            throw new RuntimeException(e);
        }
    }

    public static JsonObject getSimpleRequestJson(CloseableHttpClient httpClient, String url, Map<String, Object> params) {
        try {
            String content = getSimpleRequestContent(httpClient, url, params);
            return new JsonParser().parse(content).getAsJsonObject();
        } catch (Exception e) {
            logger.error("获取接口失败", e);
            throw new RuntimeException(e);
        }
    }
}
