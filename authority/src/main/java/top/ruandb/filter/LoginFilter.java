package top.ruandb.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import top.ruandb.common.RequestHolder;
import top.ruandb.entity.SysUser;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;

		SysUser sysUser = (SysUser) req.getSession().getAttribute("user");

		if (sysUser == null) {
			String path = req.getContextPath() + "/login.jsp";
			resp.sendRedirect(path);
			System.out.println("sssssssssssss");
			return;
		}
		RequestHolder.add(sysUser);
        RequestHolder.add(req);
		filterChain.doFilter(servletRequest, servletResponse);
		return;

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
