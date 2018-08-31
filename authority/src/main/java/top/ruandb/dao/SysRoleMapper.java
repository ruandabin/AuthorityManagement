package top.ruandb.dao;

import java.util.List;

import top.ruandb.entity.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    int countByName(SysRole record);
    
    List<SysRole> listRole();
}