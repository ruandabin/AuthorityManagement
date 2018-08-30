package top.ruandb.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

import top.ruandb.common.RequestHolder;
import top.ruandb.dao.SysRoleMapper;
import top.ruandb.entity.SysRole;
import top.ruandb.exception.AuthorityException;
import top.ruandb.service.SysRoleServiceI;
import top.ruandb.utils.BeanValidator;
import top.ruandb.utils.IpUtil;

public class SysRoleServiceImpl implements SysRoleServiceI {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public void addSysRole(SysRole sysRole) {
		BeanValidator.check(sysRole);
		if (checkExist(sysRole)) {
			throw new AuthorityException("角色名称已经存在");
		}
		sysRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder
				.getCurrentRequest()));
		sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysRole.setOperateTime(new Date());
		sysRoleMapper.insertSelective(sysRole);
	}

	@Override
	public void updateSysRole(SysRole sysRole) {
		BeanValidator.check(sysRole);
		if (checkExist(sysRole)) {
			throw new AuthorityException("角色名称已经存在");
		}
		SysRole before = sysRoleMapper.selectByPrimaryKey(sysRole.getId());
		Preconditions.checkNotNull(before, "待更新的角色不存在");
		sysRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder
				.getCurrentRequest()));
		sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysRole.setOperateTime(new Date());
		sysRoleMapper.updateByPrimaryKeySelective(sysRole);
	}

	private boolean checkExist(SysRole sysRole) {
		return sysRoleMapper.countByName(sysRole) > 0;
	}

}
