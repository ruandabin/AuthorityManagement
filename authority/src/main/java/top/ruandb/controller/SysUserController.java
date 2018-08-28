package top.ruandb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ruandb.common.JsonData;
import top.ruandb.dto.PageQuery;
import top.ruandb.dto.PageResult;
import top.ruandb.entity.SysUser;
import top.ruandb.service.SysUserServiceI;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

	@Autowired
	private SysUserServiceI sysUserService;

	@RequestMapping(value = "/save.data")
	@ResponseBody
	public JsonData saveSysUser(SysUser sysUser) {
		sysUserService.addUser(sysUser);
		return JsonData.success();
	}

	@RequestMapping(value = "/update.data")
	@ResponseBody
	public JsonData updateSysUser(SysUser sysUser) {
		sysUserService.updateUser(sysUser);
		return JsonData.success();
	}
	
	@RequestMapping(value = "/selectAll.data")
	@ResponseBody
	public PageResult<SysUser> selectAll(PageQuery pq){
		PageResult<SysUser> result = sysUserService.selectAll(pq);
		return result;
	}
}
