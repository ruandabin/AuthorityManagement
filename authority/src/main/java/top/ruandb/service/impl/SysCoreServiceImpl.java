package top.ruandb.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import top.ruandb.dao.SysAclMapper;
import top.ruandb.dao.SysRoleAclMapper;
import top.ruandb.dao.SysRoleMapper;
import top.ruandb.dao.SysRoleUserMapper;
import top.ruandb.entity.SysAcl;
import top.ruandb.service.SysCoreServiceI;

public class SysCoreServiceImpl implements SysCoreServiceI {


	@Autowired
	private SysAclMapper sysAclMapper;
	
	@Autowired
	private SysRoleUserMapper sysRoleUserMapper ;
	
	@Autowired
	private SysRoleAclMapper sysRoleAclMapper;

	// 跟用户ID查询该用户的权限
	@Override
	public List<SysAcl> getUserAclList(int userId) {
		if (isSuperAdmin()) {
			return sysAclMapper.getAll();
		}
		// 1、获取该用户所拥有的所有角色
		List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
		if (CollectionUtils.isEmpty(roleIdList)) {
			return Lists.newArrayList();
		}

		// 2、获取对应角色的所有权限点(该用户的所有权限点)
		List<Integer> userAclIdList = sysRoleAclMapper
				.getAclIdListByRoleIdList(roleIdList);
		if (CollectionUtils.isEmpty(userAclIdList)) {
			return Lists.newArrayList();
		}

		return sysAclMapper.getByIdList(userAclIdList);
	}

	// 根据角色ID查该角色
	@Override
	public List<SysAcl> getRoleAclList(int roleId) {
		List<Integer> roleAclIdList = sysRoleAclMapper
				.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
		if (CollectionUtils.isEmpty(roleAclIdList)) {
			return Lists.newArrayList();
		}
		return sysAclMapper.getByIdList(roleAclIdList);
	}

	// 判断是否超级管理员
	private boolean isSuperAdmin() {
		//TODO
		return false;
	}
}
