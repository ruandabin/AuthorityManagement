package top.ruandb.service;

import java.util.List;

import top.ruandb.dto.SysAclModuleDto;
import top.ruandb.entity.SysAclModule;

public interface SysAclModuleServiceI {
	
	public void addSysAclModule(SysAclModule sysAclModule);

	public List<SysAclModuleDto> sysAclModuleTree();

	public void updateSysAclModule(SysAclModule sysAclModule);
}
