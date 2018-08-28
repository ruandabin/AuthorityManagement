package top.ruandb.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import top.ruandb.common.JsonData;
import top.ruandb.entity.SysUser;
import top.ruandb.exception.ParamException;
import top.ruandb.service.SysUserServiceI;
import top.ruandb.utils.MD5Util;

@Controller
public class UserController {
	
	@Autowired
	private SysUserServiceI sysUserService;

	@RequestMapping("/login.page")
	public void login(HttpServletRequest request, HttpServletResponse response,SysUser sysUser) throws IOException, ServletException{
		
		String errorMsg = "" ;
		SysUser user = sysUserService.findByKeyWord(sysUser.getUsername());
		if(StringUtils.isBlank(sysUser.getUsername())){
			//throw new ParamException("用户名不可以为空");
			errorMsg = "用户名不可以为空";
		}else if(StringUtils.isBlank(sysUser.getPassword())){
			errorMsg = "密码不可以为空";
		}else if(user == null ){
			errorMsg = "不存在该用户";
		}else if(!MD5Util.encrypt(sysUser.getPassword()).equals(user.getPassword())){
			errorMsg = "用户或者密码不正确";
		}else if(user.getStatus() != 1){
			errorMsg = "用户已被冻结，请联系管理员";
		}else{
			request.getSession().setAttribute("user", sysUser);
			response.sendRedirect(request.getContextPath() +"/admin/main.page"); 
			return ;
		}
		request.setAttribute("errorMsg", errorMsg);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	
	@RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.jsp");
    }
}
