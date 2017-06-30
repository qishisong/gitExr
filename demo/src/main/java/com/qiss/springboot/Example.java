package com.qiss.springboot;

import com.qiss.springboot.servlet.MyServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by Administrator on 2017/6/16.
 */
@RestController
@EnableAutoConfiguration
//@ServletComponentScan
@ComponentScan(value={"com.qiss.springboot.web"})
public class Example {

    @RequestMapping("/")
    String home(){
        return "Hello world";
    }



    public static void main(String[] args) {
        SpringApplication.run(Example.class,args);
    }

    @RequestMapping("/index")
    public String index(){
        return "Index Page";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "Appliction hello world";
    }

    /*@Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new MyServlet(),"/xs*//*");
    }

    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet);
        servletRegistrationBean.addUrlMappings("*.do");
        servletRegistrationBean.addUrlMappings("*.rest");
        return servletRegistrationBean;
    }*/
}
