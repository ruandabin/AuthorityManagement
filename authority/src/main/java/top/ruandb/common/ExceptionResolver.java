package top.ruandb.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import top.ruandb.exception.AuthorityException;
import top.ruandb.exception.ParamException;

/**
 * 全局异常处理
 * @author rdb
 *
 */
@Slf4j
public class ExceptionResolver implements HandlerExceptionResolver {

	static final String DEFAULT_MSG = "System error";

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) {
		String url = request.getRequestURL().toString();
		ModelAndView mv ;
		if(url.endsWith(".do")){//项目定义：url以.do结尾，访问json数据
			if(e instanceof AuthorityException || e instanceof ParamException){
				JsonData result = JsonData.fail(e.getMessage());
				mv = new ModelAndView("jsonView",result.toMap());
			}else{
				log.error("unknown json exception, url:" + url, e);
				JsonData result = JsonData.fail(DEFAULT_MSG);
				mv = new ModelAndView("jsonView",result.toMap());
			}
		}else if(url.endsWith(".html")){//项目定义：url以.html结尾，访问页面
			log.error("unknown page exception, url:" + url, e);
			JsonData result = JsonData.fail(DEFAULT_MSG);
			mv = new ModelAndView("exception", result.toMap());
		}else{
			log.error("unknown exception, url:" + url, e);
			JsonData result = JsonData.fail(DEFAULT_MSG);
			mv = new ModelAndView("exception", result.toMap());
		}
		return mv;
	}
}
