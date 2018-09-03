package com.woodwang.common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AppInitServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 初始化
     * @throws ServletException
     */
    public void init() throws ServletException {
    	Logger logger  = Logger.getLogger(this.getClass());
    	logger.info("Init Servlet init .");
    }
	
    public void initRealPath() {
        // 初始化版本号信息
        String realPath = this.getServletContext().getRealPath("/").replace("\\", "/");
        // 如果以“/”结尾，则截掉，保证realPath后不包含“/”
        if (realPath.endsWith("/")) {
            realPath = realPath.substring(0, realPath.length() - 1);
        }

        // 设置应用的部署路径：所有获取类路径下配置文件的地方会引用该变量
        System.getProperties().put("basePath", realPath);
    }
    
	public void initLog4j() {
        try {
            String realPath = System.getProperty("basePath");
            String path = realPath + "/WEB-INF/classes/config/" + "log4j.properties";
            PropertyConfigurator.configure(path);
        } catch (Exception e) {
        }
    }
}
