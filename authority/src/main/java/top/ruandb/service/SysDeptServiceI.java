package top.ruandb.service;

import java.util.List;

import top.ruandb.dto.SysDeptDto;
import top.ruandb.entity.SysDept;

public interface SysDeptServiceI {

	public void addSysDept(SysDept sysdept);
	
	public List<SysDeptDto> deptTree();
	
	public void updateSysDept(SysDept sysdept);
	
}
