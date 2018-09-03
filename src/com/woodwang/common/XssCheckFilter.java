package com.woodwang.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * XSS防注入攻击过滤器
 * @author admin
 *
 */
public class XssCheckFilter implements Filter{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	// 是否开启SessionCheck过滤器
    private  boolean isEnabled = false;

    private  boolean isCheckHeader = false;
    
    private  boolean isCheckParameter = false;
    
    private  boolean isInterruptChain = false;
	
	@Override
	public void destroy() {
		logger.info("XssCheckFilter filter execute destroy.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if(!isEnabled){
    		logger.info("XSS过滤器未启用！ ");
    		//这一句很重要，是继续执行下一个拦截器的调用操作
            chain.doFilter(request, response);
            return;
    	}
		
		// 转型
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        XssRequestWrapper xssRequest = new XssRequestWrapper(httpRequest);
		logger.info("XssCheckFilter filter execute filter ,url is "+httpRequest.getRequestURL());
	
		//校验返回真，说明存在xss攻击信息
		boolean isHeaderInvalid = false;
		if(isCheckHeader) {//检查头域，则调用warpper的校验方法
			isHeaderInvalid = xssRequest.validateHeader(httpRequest);
		}
		
		//校验返回真，说明存在xss攻击信息
		boolean isParameterInvalid = true;
		if(isCheckParameter) {//检查头域，则调用warpper的校验方法
			isParameterInvalid = xssRequest.validateParameter(httpRequest);
		}
		
		//头域或者参数不合法，则说明有xss攻击威胁，根据配置决定是否返回
		if(isHeaderInvalid || isParameterInvalid){
        	//配置中断请求，则过滤器返回，不再执行后续的拦截器链条上的内容
        	if(isInterruptChain){
        		logger.info("XssCheckFilter filter execute filter check return false,用户输入非法.");
        		return;
    		}else {
    			logger.error("XssCheckFilter detect xss request: "+httpRequest.getServletPath());
    		}
        }
		
		//校验通过，则继续执行下一个过滤器
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.info("XssCheckFilter filter execute init.");
		
		//从配置文件中获取Filter的配置参数
		try {
            // 取是否开启安全过滤器
            String isEnabled = config.getInitParameter("isEnabled");
            if(isEnabled!=null && !"".equals(isEnabled)) {
            	this.isEnabled = Boolean.valueOf(isEnabled);
            }
            
            String isCheckHeader = config.getInitParameter("isCheckHeader");
            if(isCheckHeader!=null && !"".equals(isCheckHeader)) {
            	this.isCheckHeader = Boolean.parseBoolean(isCheckHeader);
            }
            
            String isCheckParameter = config.getInitParameter("isCheckParameter");
            if(isCheckParameter!=null && !"".equals(isCheckParameter)) {
            	this.isCheckParameter = Boolean.parseBoolean(isCheckParameter);
            }
            
            String isInterruptChain = config.getInitParameter("isInterruptChain");
            if(isInterruptChain!=null && !"".equals(isInterruptChain)) {
            	this.isInterruptChain = Boolean.parseBoolean(isInterruptChain);
            }
        } catch (RuntimeException e) {
            logger.error("XssCheckFilter filter init error.", e);
        }
	}
}
