package top.ruandb.dao;

import java.util.List;

import top.ruandb.entity.SysRoleAcl;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);
    
    List<Integer> getAclIdListByRoleIdList(List<Integer> roleIdList);
}