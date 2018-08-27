package top.ruandb.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ruandb.common.JsonData;
import top.ruandb.entity.SysDept;
import top.ruandb.service.SysdeptServiceI;

@Controller
@RequestMapping("/sys/dept")
public class SysdeptController {

	@Autowired
	private SysdeptServiceI sysdeptService ;
	
	@RequestMapping("/dept.page")
	public String deptPage(){
		return "dept";
	}
	
	@RequestMapping(value="/save.data")
	@ResponseBody
	public JsonData saveSysdept(SysDept sysdept){
		sysdeptService.addSysDept(sysdept);
		return JsonData.success();
	}
	
	@RequestMapping(value="/update.data")
	@ResponseBody
	public JsonData updateSysdept(SysDept sysdept){
		sysdeptService.updateSysDept(sysdept);
		return JsonData.success();
	}
	
	@RequestMapping(value="/tree.data")
	@ResponseBody
	public JsonData getSysDeptTree(){
		return JsonData.success(sysdeptService.deptTree());
	}
}
