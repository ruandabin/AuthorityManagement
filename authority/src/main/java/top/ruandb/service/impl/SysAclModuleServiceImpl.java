package top.ruandb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import top.ruandb.common.RequestHolder;
import top.ruandb.dao.SysAclMapper;
import top.ruandb.dao.SysAclModuleMapper;
import top.ruandb.dto.SysAclDto;
import top.ruandb.dto.SysAclModuleDto;
import top.ruandb.dto.SysDeptDto;
import top.ruandb.entity.SysAcl;
import top.ruandb.entity.SysAclModule;
import top.ruandb.entity.SysDept;
import top.ruandb.exception.AuthorityException;
import top.ruandb.service.SysAclModuleServiceI;
import top.ruandb.service.SysCoreServiceI;
import top.ruandb.utils.BeanValidator;
import top.ruandb.utils.IpUtil;

@Service("sysAclModuleService")
public class SysAclModuleServiceImpl implements SysAclModuleServiceI {

	// 权限模块层级连接符
	public final static String SEPARATOR = "-";
	// 权限模块顶层层级
	public final static String ROOT = "0";

	@Autowired
	private SysAclModuleMapper sysAclModuleMapper;
	
	@Autowired
	private SysCoreServiceI sysCoreService;

	@Autowired
	private SysAclMapper sysAclMapper;

	@Override
	public void addSysAclModule(SysAclModule sysAclModule) {

		BeanValidator.check(sysAclModule);
		if (checkExist(sysAclModule)) {
			throw new AuthorityException("同一层级下存在相同名称的权限模块");
		}
		sysAclModule.setLevel(calculateLevel(sysAclModule));
		sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));// TODO
		sysAclModule.setOperateTime(new Date());
		sysAclModuleMapper.insertSelective(sysAclModule);
	}

	// 更新权限模块
	@Override
	public void updateSysAclModule(SysAclModule sysAclModule) {
		BeanValidator.check(sysAclModule);
		SysAclModule beforSysAclModule = sysAclModuleMapper.selectByPrimaryKey(sysAclModule.getId());
		Preconditions.checkNotNull(beforSysAclModule, "待更新的权限模块不存在");
		if (checkExist(sysAclModule)) {
			throw new AuthorityException("同层级下已经存在相同名称的权限模块");
		}

		sysAclModule.setLevel(calculateLevel(sysAclModule));
		sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());// TODO
		sysAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));// TODO
		sysAclModule.setOperateTime(new Date());
		updateWithChild(beforSysAclModule, sysAclModule);
	}

	// 更新子权限模块
	private void updateWithChild(SysAclModule before, SysAclModule after) {
		sysAclModuleMapper.updateByPrimaryKeySelective(after);
		if (!before.getLevel().equals(after.getLevel())) {
			List<SysAclModule> beforeChild = sysAclModuleMapper.selectAllChildSysAclModule(before);
			if (CollectionUtils.isNotEmpty(beforeChild)) {
				for (SysAclModule sysAclModule : beforeChild) {
					if (sysAclModule.getLevel().indexOf(before.getLevel()) == 0) {
						sysAclModule.setLevel(
								after.getLevel().concat(sysAclModule.getLevel().substring(before.getLevel().length())));
						sysAclModuleMapper.updateByPrimaryKeySelective(sysAclModule);
					}
				}
			}
		}
	}

	// 检查权限模块是否已经存在
	private boolean checkExist(SysAclModule sysAclModule) {
		return sysAclModuleMapper.countByNameAndParentId(sysAclModule) > 0;
	}

	// 计算权限模块存级
	@SuppressWarnings("unchecked")
	private String calculateLevel(SysAclModule sysAclModule) {
		if (sysAclModule == null) {
			return null;
		}
		if (sysAclModule.getParentId() != null && sysAclModule.getParentId() != 0) {
			String parentLevel = sysAclModuleMapper.selectByPrimaryKey(sysAclModule.getParentId()).getLevel();
			return StringUtils.join(parentLevel, SEPARATOR, sysAclModule.getParentId());
		} else {
			return ROOT;
		}
	}

	@Override
	public List<SysAclModuleDto> sysAclModuleTree() {
		List<SysAclModuleDto> dtoList = Lists.newArrayList();
		List<SysAclModule> aclModuleList = sysAclModuleMapper.selectAllSysAclModule();
		for (SysAclModule sysAclModule : aclModuleList) {
			dtoList.add(SysAclModuleDto.adapt(sysAclModule));
		}
		return list2Tree(dtoList);
	}

	private List<SysAclModuleDto> list2Tree(List<SysAclModuleDto> dtoList) {
		// Multimap:<key,ArrayList<>>
		Multimap<String, SysAclModuleDto> multimap = ArrayListMultimap.create();
		List<SysAclModuleDto> rootList = Lists.newArrayList();
		for (SysAclModuleDto sysAclModuleDto : dtoList) {
			multimap.put(sysAclModuleDto.getLevel(), sysAclModuleDto);
			if (sysAclModuleDto.getLevel() != null && sysAclModuleDto.getLevel().equals(ROOT)) {
				rootList.add(sysAclModuleDto);
			}
		}
		// 排序
		rootList = rootList.stream().sorted((e1, e2) -> e1.getSeq() - e2.getSeq()).collect(Collectors.toList());
		transform2Tree(rootList, multimap);
		return rootList;
	}

	private void transform2Tree(List<SysAclModuleDto> fList, Multimap<String, SysAclModuleDto> multimap) {
		for (int i = 0; i < fList.size(); i++) {
			SysAclModuleDto sysAclModuleDto = fList.get(i);
			String sunLevel = sysAclModuleDto.getLevel().concat(SEPARATOR).concat(sysAclModuleDto.getId().toString());
			List<SysAclModuleDto> sList = (List<SysAclModuleDto>) multimap.get(sunLevel);
			if (CollectionUtils.isNotEmpty(sList)) {
				sList = sList.stream().sorted((w1, w2) -> w1.getSeq() - w2.getSeq()).collect(Collectors.toList());
				fList.get(i).setChildren(sList);
				transform2Tree(sList, multimap);
			}
		}
	}

	// 根据角色获取对应的权限树
	@Override
	public List<SysAclModuleDto> roleAclTree(int roleId) {
		// 1、当前登录用户已分配的权限点
		List<SysAcl> userAclList = sysCoreService.getUserAclList(RequestHolder.getCurrentUser().getId());
		// 2、当前角色分配的权限点
		List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
		// 3、当前系统所有权限点
		List<SysAcl> aclAllList = sysAclMapper.getAll();

		// 将俩个list转换成id set，方便判断acl是否在这两个当中
		Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
		Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

		List<SysAclDto> aclDtoAllList = Lists.newArrayList();
		for (SysAcl acl : aclAllList) {
			SysAclDto aclDto = SysAclDto.adapt(acl);
			if (userAclIdSet.contains(acl.getId())) {
				aclDto.setHasAcl(true);
			}

			if (roleAclIdSet.contains(acl.getId())) {
				aclDto.setChecked(true);
			}
			aclDtoAllList.add(aclDto);
		}

		return null;
	}

	private List<SysAclModuleDto> aclListToTree(List<SysAclDto> aclDtoList) {
		return null;
	}
}
