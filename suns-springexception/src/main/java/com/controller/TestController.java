package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exception.WebRuntimeException;

@Controller
public class TestController {
    

    @RequestMapping(value = "/ex1", method = RequestMethod.GET)
    public String ex1(HttpServletRequest request, HttpServletResponse response) throws WebRuntimeException{
    	throw new WebRuntimeException("0", "123");
        //return "index";
    }
    
    @RequestMapping(value = "/ex2", method = RequestMethod.GET)
    public @ResponseBody String ex2(HttpServletRequest request, HttpServletResponse response) throws WebRuntimeException{
    	throw new WebRuntimeException("0", "1234");
        //return "index";
    }
    
    @RequestMapping(value = "/aop1", method = RequestMethod.POST)
    public @ResponseBody String aopt1(HttpServletRequest request, HttpServletResponse response) throws WebRuntimeException{
       return "{state:0,aa:123}";
    }
    
    @RequestMapping(value = "/aop2", method = RequestMethod.GET)
    public @ResponseBody String aopt2(HttpServletRequest request, HttpServletResponse response) throws WebRuntimeException{
       return "{state:0,aa:123}";
    }
    

}
