package com.example.o2o.interceptor.shopadmin;

import com.example.o2o.entity.PersonInfo;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // get user from session
        Object userObj = request.getSession().getAttribute("user");
        if (userObj != null) {
            // get userInfo, if it's not null then transform user info to PersonInfo Obj
            PersonInfo user = (PersonInfo) userObj;
            // check if it's null. Make sure userId is not null and account status == 1 and user type is shop owner
            if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1)
                return true;
        }
        // if the above requirements cannot be met, then redirect to account login in page
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open('" + request.getContextPath() + "/local/login?usertype=2','_self')");
        out.println("</script>");
        out.println("</html>");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
