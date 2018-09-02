package top.ruandb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import top.ruandb.common.RequestHolder;
import top.ruandb.dao.SysAclMapper;
import top.ruandb.dao.SysRoleMapper;
import top.ruandb.dto.SysAclDto;
import top.ruandb.dto.SysAclModuleDto;
import top.ruandb.dto.SysRoleDto;
import top.ruandb.entity.SysAcl;
import top.ruandb.entity.SysRole;
import top.ruandb.exception.AuthorityException;
import top.ruandb.service.SysAclServiceI;
import top.ruandb.service.SysCoreServiceI;
import top.ruandb.service.SysRoleServiceI;
import top.ruandb.utils.BeanValidator;
import top.ruandb.utils.IpUtil;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleServiceI {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	

	@Override
	public void addSysRole(SysRole sysRole) {
		BeanValidator.check(sysRole);
		if (checkExist(sysRole)) {
			throw new AuthorityException("角色名称已经存在");
		}
		sysRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
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
		sysRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
		sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysRole.setOperateTime(new Date());
		sysRoleMapper.updateByPrimaryKeySelective(sysRole);
	}

	private boolean checkExist(SysRole sysRole) {
		return sysRoleMapper.countByName(sysRole) > 0;
	}

	@Override
	public List<SysRoleDto> listRole() {
		List<SysRole> roleList = sysRoleMapper.listRole();
		List<SysRoleDto> roleDtoList = Lists.newArrayList();
		for (SysRole role : roleList) {
			roleDtoList.add(SysRoleDto.adapt(role));
		}
		return roleDtoList;
	}

	
}
