package com.exception.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public class BaseControllerExceptionHandler {
	@ExceptionHandler
	public ModelAndView exp(HttpServletRequest request, Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error");
		request.setAttribute("exMsg", ex);
		
		return mv;
	}
}
