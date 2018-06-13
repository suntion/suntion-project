package com.aspects;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * 
 * @author long
 *
 */
@Component
@Aspect
public class TestAspect {
    /**
     * 
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 切入表达式 execution(* *..controller..*.*(..))
     */
    @Pointcut("execution(* com..*.*(..))")
    public void ctrolAllAspect() {}
   
    
    /**
     * @param joinPoint 切入点
     */
    @Before("ctrolAllAspect()")
    public void doBeforeLog(JoinPoint joinPoint) {
        try {
            StringBuffer mgsBuffer = new StringBuffer();
            //请求方法
            String methodStr = joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName() + "()";
            mgsBuffer.append("Method:" + methodStr + "");

            //请求参数 
            StringBuilder param = getParamStr(joinPoint);
            mgsBuffer.append(",Params:" + param + "");

            // 打印
            logger.info(mgsBuffer.toString());
        } catch (Throwable e) {
            logger.error("日志解析出错!", e);
        }
    }
    
    /**
     * 获取参数
     * @param joinPoint 切点
     * @return 参数
     */
    private static StringBuilder getParamStr(JoinPoint joinPoint) {

        String[] names = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] objArr = joinPoint.getArgs();
        if (ArrayUtils.isEmpty(names)) {
            return new StringBuilder("");
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < names.length; i++) {
            if (objArr[i] instanceof HttpServletRequest) {
                HttpServletRequest hreq = (HttpServletRequest) objArr[i];
                Map<String, String[]> params = hreq.getParameterMap();
                String queryString = "";
                for (String key : params.keySet()) {
                    String[] values = params.get(key);
                    for (int k = 0; k < values.length; k++) {
                        String value = values[k];
                        queryString += key + "=" + value + "&";
                    }
                }
                if (queryString.length() >= 1) {
                    queryString = "?" + queryString.substring(0, queryString.length() - 1);
                }
                queryString = hreq.getProtocol() + " " + hreq.getMethod() + " " + hreq.getRequestURL() + queryString;
                sb.append(names[i] + " : " + queryString + ",");
            } else {
                sb.append(names[i] + " : " + objArr[i] + ",");
            }
        }
        if (sb.length() >= 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb;
    }

}
