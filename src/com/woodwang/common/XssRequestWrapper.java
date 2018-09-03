package com.woodwang.common;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * RequestWrapper，校验请求参数和头域信息
 * @author admin
 *
 */
public class XssRequestWrapper  extends HttpServletRequestWrapper {  
  
    public XssRequestWrapper(HttpServletRequest request) {  
        super(request);  
    }  
  
    //校验请求参数
	public boolean validateParameter(HttpServletRequest httpRequest) {
		Map<String, String[]> submitParams = this.getParameterMap();
		Set<String> submitNames = submitParams.keySet();
		for(String submitName : submitNames){
			Object submitValues = submitParams.get(submitName);
			if(submitValues instanceof String){
				if(isContentMatchXss((String)submitValues)){
					return true;
				}
			}else if(submitValues instanceof String[]){
				for(String submitValue : (String[])submitValues){
					if(isContentMatchXss((String)submitValue)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean validateHeader(HttpServletRequest httpRequest) {
		Enumeration<String> headerParams = this.getHeaderNames();
		while(headerParams.hasMoreElements()){
			String headerName = headerParams.nextElement();
			String headerValue = this.getHeader(headerName);
			if(isContentMatchXss(headerValue)){
				return true;
			}
		}
		return false;
	}  
     
	//匹配html标签
	public static  boolean isContentMatchXss(String value) {
		String htmlPatternStr= ".*[<|>].*";
	    Pattern htmlPattern = Pattern.compile(htmlPatternStr) ;
	    return htmlPattern.matcher(value).matches();
	}
	
	public static void main(String[] args) {
		System.out.println(isContentMatchXss("<script>hello</script>"));;
	}
}  
