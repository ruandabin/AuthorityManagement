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
	
	@RequestMapping(value="/save",produces="application/json;charset=UTF-8")
	@ResponseBody
	public JsonData saveSdept(SysDept sysdept){
		sysdeptService.addSysDept(sysdept);
		return JsonData.success();
	}
}
