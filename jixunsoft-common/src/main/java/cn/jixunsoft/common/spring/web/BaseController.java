package cn.jixunsoft.common.spring.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    @ModelAttribute
    public void setReqestAndResponse(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @return the response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * @return the session
     */
    public HttpSession getSession() {
        return session;
    }
}
