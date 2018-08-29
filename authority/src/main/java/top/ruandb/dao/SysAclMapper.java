package top.ruandb.dao;

import java.util.List;
import java.util.Map;

import top.ruandb.dto.SysAclDto;
import top.ruandb.entity.SysAcl;

public interface SysAclMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SysAcl record);

	int insertSelective(SysAcl record);

	SysAcl selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysAcl record);

	int updateByPrimaryKey(SysAcl record);

	List<SysAclDto> selectAll(Map<String, Object> map);

	int countAll(Map<String, Object> map);
	
	int countByNameAndModuleId(SysAcl record);
}