package top.ruandb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ruandb.common.JsonData;
import top.ruandb.entity.SysAclModule;
import top.ruandb.entity.SysDept;
import top.ruandb.service.SysAclModuleServiceI;

@Controller
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {

	@Autowired
	private SysAclModuleServiceI sysAclModuleService;
	
	@RequestMapping("/aclModule.page")
	public String deptPage(){
		return "aclModule";
	}
	
	@RequestMapping(value="/save.data")
	@ResponseBody
	public JsonData saveSysAclModule(SysAclModule sysAclModule){
		sysAclModuleService.addSysAclModule(sysAclModule);
		return JsonData.success();
	}
	
	@RequestMapping(value="/update.data")
	@ResponseBody
	public JsonData updateSysAclModule(SysAclModule sysAclModule){
		sysAclModuleService.updateSysAclModule(sysAclModule);
		return JsonData.success();
	}
	
	@RequestMapping(value="/tree.data")
	@ResponseBody
	public JsonData getSysAclModuleTree(){
		return JsonData.success(sysAclModuleService.sysAclModuleTree());
	}
}
