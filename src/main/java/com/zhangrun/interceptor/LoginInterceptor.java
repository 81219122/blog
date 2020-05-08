package com.zhangrun.interceptor;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/30 13:24
 * 登录过滤器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /*预处理 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if(request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin");
            //attributes.addFlashAttribute("message","您没有该权限，请联系管理员");
            return false;
        }
        return true;
    }


}
