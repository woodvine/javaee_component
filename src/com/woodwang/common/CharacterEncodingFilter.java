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
 * 自定义的字符编码拦过滤器
 * 
 * @author admin
 *
 */
public class CharacterEncodingFilter implements Filter {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
    //默认编码类型
    private static String userEncoding = "utf-8";

    /**
     * (non-Javadoc) 过滤器初始化，初始化配置文件中的配置信息
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
    	logger.info("CharacterEncodingFilter execute init.");
        try {
            // 获取在web.xml 设置的参数值：用户定义的编码
            String encoding = config.getInitParameter("encoding");
            if (!"".equals(encoding)) {
                userEncoding = encoding;
            }
        } catch (RuntimeException e) {
        	logger.error("CharacterEncodingFilter execute init encouter error",e);
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        try {
            // 转型
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
           
            logger.info("CharacterEncodingFilter execute doFilter:"+httpRequest.getRequestURL());
            
            //设置编码为用户定义的编码
            if (null != userEncoding) {
                httpRequest.setCharacterEncoding(userEncoding);
                httpResponse.setCharacterEncoding(userEncoding);
            }
            chain.doFilter(httpRequest, httpResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void destroy() {
    	 logger.info("CharacterEncodingFilter execute destroy.");
        userEncoding = null;
    }
}
