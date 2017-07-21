package cn.jixunsoft.common.spring.m9;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import cn.jixunsoft.common.base.FrameworkConstants;
import cn.jixunsoft.common.secure.SecurityUtils;
import cn.jixunsoft.common.spring.PropertiesUtils;
import cn.jixunsoft.common.thread.ThreadContext;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class M9FreeMarkerView extends FreeMarkerView {

    private boolean m9Switch = PropertiesUtils.getBoolean(FrameworkConstants.M9_SWITCH, false);

    public static final Logger logger = Logger.getLogger(M9FreeMarkerView.class);

    @Override
    protected void processTemplate(Template template, SimpleHash model, HttpServletResponse response) throws IOException, TemplateException {

        if (!m9Switch) {
            super.processTemplate(template, model, response);
            return;
        }

        String m9Tag = (String) ThreadContext.get(FrameworkConstants.M9_TAG);
        if (!StringUtils.equals(m9Tag, FrameworkConstants.M9_TAG_VLAUE)) {
            super.processTemplate(template, model, response);
            return;
        }

        // 模板不是.json结尾时, 响应数据不加密
        if (!StringUtils.endsWith(template.getName(), ".json")) {
            super.processTemplate(template, model, response);
            return;
        }

        StringWriter sw = null;

        try {
            sw = new StringWriter();
            template.process(model, sw);

            String result = null;

            String content = sw.toString();
            if (StringUtils.isNotEmpty(content)) {
                result = SecurityUtils.securityEncode(content);
            }
            response.getWriter().write(result);
        } catch(Exception e) {
            logger.error("reponse write String failed", e);
            throw e;
        }  finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (Exception e) {
                    logger.error("sw close failed", e);
                }
            }
        }
    }
}
