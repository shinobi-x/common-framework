package cn.jixunsoft.common.spring.m9;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import cn.jixunsoft.common.base.FrameworkConstants;
import cn.jixunsoft.common.secure.SecurityUtils;
import cn.jixunsoft.common.spring.PropertiesUtils;
import cn.jixunsoft.common.thread.ThreadContext;

import com.fasterxml.jackson.databind.JavaType;

public class M9MappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private boolean m9Switch = PropertiesUtils.getBoolean(FrameworkConstants.M9_SWITCH, false);

    public static final Logger logger = Logger.getLogger(M9MappingJackson2HttpMessageConverter.class);

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

        if (!m9Switch) {
            return super.read(type, contextClass, inputMessage);
        }

        String m9Tag = (String) ThreadContext.get(FrameworkConstants.M9_TAG);
        if (!StringUtils.equals(m9Tag, FrameworkConstants.M9_TAG_VLAUE)) {
            return super.read(type, contextClass, inputMessage);
        }

        try {
            String securityData = IOUtils.toString(inputMessage.getBody(), "utf-8");
            String data = SecurityUtils.securityDecode(securityData);
            byte[] bytes = data.getBytes();

            JavaType javaType = getJavaType(type, contextClass);

            return this.objectMapper.readValue(bytes, javaType);
        } catch (Exception e) {
            logger.error("m9 decode failed", e);
            throw e;
        }
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        if (!m9Switch) {
            super.writeInternal(object, type, outputMessage);
            return;
        }

        String m9Tag = (String) ThreadContext.get(FrameworkConstants.M9_TAG);
        if (!StringUtils.equals(m9Tag, FrameworkConstants.M9_TAG_VLAUE)) {
            super.writeInternal(object, type, outputMessage);
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(object);
            String result = SecurityUtils.securityEncode(json);
            outputMessage.getBody().write(result.getBytes());            
        } catch (Exception e) {
            logger.error("m9 encode failed", e);
            throw e;
        }

    }

}
