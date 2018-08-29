package top.ruandb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ruandb.common.JsonData;
import top.ruandb.dto.PageQuery;
import top.ruandb.dto.SysAclDto;
import top.ruandb.entity.SysAcl;
import top.ruandb.service.SysAclServiceI;

@Controller
@RequestMapping("/sys/acl")
public class SysAclController {

	@Autowired
	private SysAclServiceI sysAclService;

	@RequestMapping(value = "/save.data")
	@ResponseBody
	public JsonData saveSysAcl(SysAcl sysAcl) {
		sysAclService.addAcl(sysAcl);
		return JsonData.success();
	}

	@RequestMapping(value = "/update.data")
	@ResponseBody
	public JsonData updateSysAcl(SysAcl sysAcl) {
		sysAclService.updateAcl(sysAcl);
		return JsonData.success();
	}

	@RequestMapping(value = "/selectAll.data")
	@ResponseBody
	public JsonData selectAll(SysAclDto sysAclDto, PageQuery pq) {
		return JsonData.success(sysAclService.selectAll(sysAclDto, pq));
	}
}
