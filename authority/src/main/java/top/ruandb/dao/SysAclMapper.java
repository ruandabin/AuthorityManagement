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

	// 带条件分页查询所有
	List<SysAclDto> selectAll(Map<String, Object> map);

	// 分页带条件分页count
	int countAll(Map<String, Object> map);

	int countByNameAndModuleId(SysAcl record);

	List<SysAcl> getByIdList(List<Integer> idList);
	
	List<SysAcl> getAll();
}