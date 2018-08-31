package top.ruandb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ruandb.common.JsonData;
import top.ruandb.entity.SysRole;
import top.ruandb.service.SysRoleServiceI;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

	@Autowired
	private SysRoleServiceI sysRoleService;
	
	@RequestMapping("/role.page")
	public String rolePage(){
		return "role";
	}
	
	@RequestMapping("/save.data")
	@ResponseBody
	public JsonData saveSysRole(SysRole sysRole){
		sysRoleService.addSysRole(sysRole);
		return JsonData.success();
	}
	
	@RequestMapping("/update.data")
	@ResponseBody
	public JsonData updateSysRole(SysRole sysRole){
		sysRoleService.updateSysRole(sysRole);
		return JsonData.success();
	}
	
	@RequestMapping("/list.data")
	@ResponseBody
	public JsonData listRole(){
		return JsonData.success(sysRoleService.listRole());
	}
}
