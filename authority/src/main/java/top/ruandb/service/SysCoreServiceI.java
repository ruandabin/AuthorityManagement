package top.ruandb.service;

import java.util.List;

import top.ruandb.entity.SysAcl;
//权限核心服务
public interface SysCoreServiceI {

	//获取指定用户的权限点列表
	public List<SysAcl> getUserAclList(int userId);
	
	//获取指定角色的权限点列表
	public List<SysAcl> getRoleAclList(int roleId);
}
