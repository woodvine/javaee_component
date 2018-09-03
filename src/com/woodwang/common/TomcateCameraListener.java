package com.woodwang.common;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * 自定义TomcateCameraListener
 * @author admin
 *
 */
public class TomcateCameraListener implements ServletContextListener{
	private Logger logger = Logger.getLogger(TomcateCameraListener.class);
	
	private long start = 0;
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("listener TomcateCameraListener execute  contextInitialized.");
		logger.info("Tomcate容器停止时间："+new Date());
		
		long end = System.currentTimeMillis();
		
		logger.info("Tomcate容器此次运行累计时间："+(end-start)/1000/60000+"(分钟)");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("listener TomcateCameraListener execute  contextDestroyed.");
		start = System.currentTimeMillis();
		
		logger.info("Tomcate容器启动时间："+new Date());
	}

}
