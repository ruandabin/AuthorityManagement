package top.ruandb.common;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 *获取全局的ApplicationContext
 * @author rdb
 *
 */
public class ApplicationContextHelper implements ApplicationContextAware{

	@SuppressWarnings("unused")
	private static ApplicationContext applicationContext = null  ;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		applicationContext = context;
	}
	
	
}
