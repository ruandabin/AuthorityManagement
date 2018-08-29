package top.ruandb.dao;

import java.util.List;

import top.ruandb.entity.SysAclModule;

public interface SysAclModuleMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SysAclModule record);

	int insertSelective(SysAclModule record);

	SysAclModule selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysAclModule record);

	int updateByPrimaryKey(SysAclModule record);

	int countByNameAndParentId(SysAclModule record);

	List<SysAclModule> selectAllSysAclModule();

	List<SysAclModule> selectAllChildSysAclModule(SysAclModule record);
}