package cn.jixunsoft.common.spring.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;

import cn.jixunsoft.common.base.FrameworkConstants;
import cn.jixunsoft.common.thread.ThreadContext;

/**
 * 自定义DispatcherServlet
 * 
 * @author Danfo Yam
 *
 */
@SuppressWarnings("serial")
public class FrameworkDispatcherServlet extends DispatcherServlet {

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            init(request, response);
            super.doService(request, response);
        } finally {
            ThreadContext.destory();
        }
    }

    private void init(HttpServletRequest request, HttpServletResponse response) {
        initM9Tag(request);
    }

    private void initM9Tag(HttpServletRequest request) {

        String path = request.getRequestURI();

        // 以.json或.html结尾的不添加加密标识
        if (StringUtils.endsWithIgnoreCase(path, ".json") || StringUtils.endsWithIgnoreCase(path, ".html")) {
            return;
        }

        ThreadContext.put(FrameworkConstants.M9_TAG, FrameworkConstants.M9_TAG_VLAUE);
    }
}
