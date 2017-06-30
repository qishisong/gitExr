package com.qiss.springboot.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/6/19.
 * 在spring boot中添加自己的servlet有两种方法，代码注册servlet和注解自动注册（Filter和Listener也是如此）
 * 1.代码注册通过ServletRegistrationBean、FilterRegistrationBean和ServletListenerRefistrationBean获得控制，也可以通过实现ServletContextInitializer接口直接注册
 * 2.在springbootApplication上使用@ServletComponentScan注解后，servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册，无需其他代码
 *
 * 拦截器匹配的优先级是从精确到模糊，复合条件的Servlet并不会都执行
 */
@WebServlet(urlPatterns = "/xs/myservlet",description = "Servlet的说明")
public class MyServlet2 extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException{
        System.out.println(">>>>>>>>>>>>>>>doGet2()<<<<<<<<<<<<<<<");
        doPost(req,resp);
    }

    @Override
    protected void  doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException{
        System.out.println(">>>>>>>>>>>>>>>doPost2()<<<<<<<<<<<<<<<");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>大家好，我的名字叫Servlet2</h1>");
        out.println("</body>");
        out.println("</html>");

    }

}
