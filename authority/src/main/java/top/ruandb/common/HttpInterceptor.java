package top.ruandb.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import top.ruandb.utils.JsonUtil;

/**
 * 全局拦截器，拦截url
 * 
 * @author rdb
 *
 */
@Slf4j
public class HttpInterceptor implements HandlerInterceptor {

	private static final String START_TIME = "requestStartTime";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse responses, Object handler) throws Exception {
		String url = request.getRequestURL().toString();
		Map params = request.getParameterMap();
		log.info("request start. url:{}, params:{}", url,
				JsonUtil.obj2Sting(params));
		request.setAttribute(START_TIME, System.currentTimeMillis());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse responses, Object handler, Exception e)
			throws Exception {
		String url = request.getRequestURL().toString();
		long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request completed. url:{}, cost:{}", url, end - start);
        RequestHolder.remove();//移除Threadlocal中的用户和请求信息
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		RequestHolder.remove();//移除Threadlocal中的用户和请求信息
	}

}
