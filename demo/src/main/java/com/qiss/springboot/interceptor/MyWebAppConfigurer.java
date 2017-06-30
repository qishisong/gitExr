package com.qiss.springboot.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/6/21.
 */
@Component
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        /**
         * 多个拦截器组成一个拦截器链
         * addPathPatterns用于
         */
        registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/my/**");
        registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
