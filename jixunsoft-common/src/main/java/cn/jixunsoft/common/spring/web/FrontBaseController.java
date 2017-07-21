package cn.jixunsoft.common.spring.web;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public class FrontBaseController extends BaseController {

    private static final Logger logger = Logger.getLogger(FrontBaseController.class);

    public ModelAndView renderJson(String template, Map<String, Object> params) {

        ModelAndView mav = new ModelAndView(template);

        if (mav.getModel().get("code") == null) {
            mav.getModel().put("code", 200);
        }

        if (mav.getModel().get("msg") == null) {
            mav.getModel().put("msg", "OK");
        }
        mav.addAllObjects(params);
//        response.addHeader("Content-type", "application/json;charset=utf-8");
//        response.addHeader("Cache-Control", "no-cache");
        return mav;
    }

    public ModelAndView jsonSucess() {
//        getResponse().addHeader("Content-type", "application/json;charset=utf-8");
        return new ModelAndView("common/success.json");
    }

    public ModelAndView jsonFailed(int code, String msg) {
        ModelAndView mav = new ModelAndView("common/failed.json");
        mav.getModel().put("code", code);
        mav.getModel().put("msg", msg);
//        response.addHeader("Content-type", "application/json;charset=utf-8");
        return mav;
    }

    public ModelAndView renderHtml(String template, Map<String, Object> params) {
        ModelAndView mav = new ModelAndView(template);
        mav.addAllObjects(params);
//        response.setContentType("text/html; charset=utf8");
        return mav;
    }

    public ModelAndView renderText(String content) {

        ModelAndView mav = new ModelAndView("common/response");
        mav.getModel().put("content", content);
//        response.setContentType("text/plain; charset=utf8");

        return mav;
    }

    public void forward(String url) {

        try {
            request.setCharacterEncoding("UTF-8");
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            logger.error("forward failed" + url, e);
            throw new RuntimeException("系统异常", e);
        }
    }

    public void redirectUrl(String url) {

        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            logger.error("redirect failed:" + url, e);
            throw new RuntimeException("系统异常", e);
        }
    }

    public ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }

}
