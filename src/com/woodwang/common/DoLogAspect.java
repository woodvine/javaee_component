package com.woodwang.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class DoLogAspect {
	private Logger logger = Logger.getLogger(DoLogAspect.class);
	
	/**
	 * 以注解为切点：
	 * 凡是在方法上添加了@ControllerAnnotation的方法会被加入增强逻辑
	 */
	@Pointcut("@annotation(com.woodwang.common.ControllerLogAnnotation)")
	public void syslogAnnotation(){
		logger.info("切点");
	}
	
	/**
	 * 以某一个包下所有public方法为切点
	 * 通配符的含义:
	 */
	@Pointcut("execution(public * com.woodwang.controller.*.*(..))")
	public void controllerExecution(){
		logger.info("切点");
	}
	
	@Around("controllerExecution()")  
    public Object around(ProceedingJoinPoint point) throws Throwable {  
		logger.info("LogInterceptor around controller method begin.");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder  
                .getRequestAttributes()).getRequest();
        request.setCharacterEncoding("utf-8") ;
        String targetName = point.getTarget().getClass().getName(); 
        String methodName = point.getSignature().getName();
        
        //系统日志需要的参数：
   	 	String opertionType = request.getParameter("opertionType");    
   	 	String opertionModule = request.getParameter("opertionModule");    
   	 	String opertionObj = request.getParameter("opertionObj");  
   	 	
        Object object = null;  
        try {   
        	 //执行Controller方法
        	 object = point.proceed(); 
        	 
        	 //控制下记录日志的操作：如果必要信息没有传递的话，就不记录日志
        	 if(opertionType==null||opertionModule==null||"".equals(opertionModule)){
        		 logger.info("没有传递操作类型和操作模块信息，不记录日志.");
        		 return object;
        	 }
        	
        	 //所有的Controller类的操作都返回ResultData，可以取到操作结果
        	logger.info("current request info:操作类型"+opertionType+",操作模块:"+opertionModule+",操作对象:"+opertionObj);
        } catch (Exception e) {  
            logger.error("AOP注入日志操作异常",e);
            throw e;  
        }  
        
        logger.info("Aspect 注入执行完成,targetClassName:"+targetName+",aroundMethodName:"+methodName);
        return object;  
    }  
}

