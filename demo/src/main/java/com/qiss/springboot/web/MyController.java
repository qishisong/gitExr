package com.qiss.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/21.
 */
@Controller
@RequestMapping("/my")
public class MyController {

    @RequestMapping("/test")
    public String test(ModelMap model){
        model.put("time", new Date());
        model.put("message", "这是测试的内容。。。");
        model.put("toUserName", "张三");
        model.put("fromUserName", "老许");
        return "welcome";
    }

    @RequestMapping("/test1")
    @ResponseBody
    public String test1(){
        return "myTest1";
    }


}
