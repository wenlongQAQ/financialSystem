package com.example.personalfinancialmanagementsystem.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class ProjectFilter implements HandlerInterceptor {
    /**
     * 拦截尚未登录的用户
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("login")||
                requestURI.contains("logon")||
                requestURI.contains("common")||
                requestURI.contains("forgetPassword")||
                requestURI.contains("index")||
                requestURI.contains("register")||
                requestURI.contains("js")||
                requestURI.contains("css")||
                requestURI.contains("element-ui")) {
            return true;
        }
        if (request.getSession().getAttribute("userId")==null){
            response.sendRedirect("/static/front/login.html");
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
