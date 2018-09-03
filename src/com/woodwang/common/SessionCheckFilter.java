package com.woodwang.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.woodwang.bean.User;

/**
 * 用户是否登陆存在会话信息校验Filter
 * @author admin
 *
 */
public class SessionCheckFilter implements Filter{

	private Logger logger = Logger.getLogger(this.getClass());
	
	// 是否开启SessionCheck过滤器
    private  boolean isEnabled = false;

    /**
     * 会话超时登陆页面
     */
    private  String indexPage = null;
	
	@Override
	public void destroy() {
		logger.info("SessionCheckFilter filter execute destroy.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if(!isEnabled){
    		logger.info("SessionCheck过滤器未启用！ ");
    		//这一句很重要，是继续执行下一个拦截器的调用操作
            chain.doFilter(request, response);
            return;
    	}
		
		// 转型
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		StringBuffer requestURL = httpRequest.getRequestURL();
		logger.info("SessionCheckFilter filter execute filter ,url is "+requestURL);
		if(isNotFilter(requestURL.toString())) {
			chain.doFilter(request, response);
            return;
		}
		
		//判断是否存在会话信息，如果不存在则跳转，否则继续执行下一个Filter
		User user = (User) httpRequest.getSession().getAttribute(httpRequest.getSession().getId());
		if(user==null) {
			logger.info("用户会话已过期或未登录，安全过滤器禁止访问，并跳转到登陆页面。");
			
			httpRequest.getRequestDispatcher(indexPage).forward(httpRequest, httpResponse);
        	return;
		}
		
		//已经登陆，则继续执行下一个过滤器
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.info("SessionCheckFilter filter execute init.");
		
		//从配置文件中获取Filter的配置参数
		try {
            // 取是否开启安全过滤器
            String isEnabled = config.getInitParameter("isEnabled");
            if(isEnabled!=null && !"".equals(isEnabled)) {
            	this.isEnabled = Boolean.valueOf(isEnabled);
            }
            
            //indexPage
            String indexPage = config.getInitParameter("indexPage");
            if(indexPage!=null && !"".equals(indexPage)) {
            	this.indexPage = indexPage;
            }
        } catch (RuntimeException e) {
            logger.error("SessionCheckFilter filter init error.", e);
        }
	}

	private boolean isNotFilter(String url) {
		List<String> notFilter = new ArrayList<String>();
		notFilter.add("login");
		notFilter.add("index.jsp");
		
		for(String s:notFilter) {
			if(url.contains(s)) {
				return true;
			}
		}
		
		return false;
	}
}
