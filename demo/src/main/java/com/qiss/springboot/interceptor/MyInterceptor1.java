package com.qiss.springboot.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/6/21.
 * 实现自定义的拦截器只需要3步
 * 1.创建我们自己的拦截器类并实现HandlerInterceptor接口
 * 2.创建一个java类继承webmvcConfigurerAdapter,并重写addInterceptors方法
 * 3.实例化我们自定义的拦截器，必须将对象手动添加到拦截器链中（在addInterceptors方法中添加）
 */
public class MyInterceptor1 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println(">>>>>MyInterceptor1<<<<<<在请求处理之前进行调用（controller方法调用之前）");
        return true;//只有返回true才会继续向下执行，返回false取消当前请求
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println(">>>>>MyInterceptor1<<<<<<请求处理之后进行调用，但是在视图被渲染之前（controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println(">>>>>MyInterceptor1<<<<<<在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }
}
