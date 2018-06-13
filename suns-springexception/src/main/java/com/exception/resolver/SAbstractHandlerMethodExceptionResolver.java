package com.exception.resolver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;

/**
 * HandlerExceptionResolver handler 就是controller类
 * AbstractHandlerMethodExceptionResolver  handler  就是 handlerMethod
 * ExceptionHandlerExceptionResolver  处理@ExceptionHandler 和 @ControllerAdvice
 * 
 * @author long
 *
 */
public class SAbstractHandlerMethodExceptionResolver extends AbstractHandlerMethodExceptionResolver {  
  
	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex) {
		//判断有没有@ResponseBody的注解
		ResponseBody body = handlerMethod.getMethodAnnotation(ResponseBody.class);
		
		if (body == null) {
			System.out.println("没有body");
	        Map<String, Object> model = new HashMap<String, Object>();  
	        model.put("errMsg", ex);
	        return new ModelAndView("error", model);
		} else {
			System.out.println("有 body");
			response.setStatus(HttpStatus.OK.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);//避免乱码
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control","no-cache,must-revalidate");
			
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.write("错误");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ModelAndView();
		}
	}  

}
