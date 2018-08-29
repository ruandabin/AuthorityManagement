package top.ruandb.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import top.ruandb.common.RequestHolder;
import top.ruandb.dao.SysAclMapper;
import top.ruandb.dto.PageQuery;
import top.ruandb.dto.PageResult;
import top.ruandb.dto.SysAclDto;
import top.ruandb.entity.SysAcl;
import top.ruandb.exception.AuthorityException;
import top.ruandb.service.SysAclServiceI;
import top.ruandb.utils.BeanValidator;
import top.ruandb.utils.IpUtil;
import top.ruandb.utils.StringUtil;
@Service("sysAclService")
public class SysAclServiceImpl implements SysAclServiceI {

	@Autowired
	private SysAclMapper sysAclMapper;

	// 新增权限
	@Override
	public void addAcl(SysAcl sysAcl) {
		BeanValidator.check(sysAcl);
		if (checkExist(sysAcl)) {
			throw new AuthorityException("当前权限模块下面存在相同名称的权限点");
		}
		sysAcl.setCode(generateCode());
		sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
		sysAcl.setOperateTime(new Date());
		sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysAclMapper.insertSelective(sysAcl);
	}

	// 更新权限
	@Override
	public void updateAcl(SysAcl sysAcl) {
		BeanValidator.check(sysAcl);
		if (checkExist(sysAcl)) {
			throw new AuthorityException("当前权限模块下面存在相同名称的权限点");
		}
		SysAcl before = sysAclMapper.selectByPrimaryKey(sysAcl.getId());
		Preconditions.checkNotNull(before, "待更新的权限点不存在");
		sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
		sysAcl.setOperateTime(new Date());
		sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysAclMapper.updateByPrimaryKeySelective(sysAcl);
	}

	// 检查同权限模块下是否有相同的权限点
	private boolean checkExist(SysAcl sysAcl) {
		return sysAclMapper.countByNameAndModuleId(sysAcl) > 0;
	}

	// 生成权限编码
	private String generateCode() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_"
				+ (int) (Math.random() * 100);
	}

	// 分页查询
	@Override
	public PageResult<SysAclDto> selectAll(SysAclDto sysAclDto, PageQuery pq) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("start", pq.getStart());
		map.put("rows", pq.getRows());
		map.put("name", StringUtil.formatLike(sysAclDto.getName()));
		map.put("moduleId", sysAclDto.getAclModuleId());
		String formatLevel = sysAclDto.getModuleLevel() == null ? null
				: sysAclDto.getModuleLevel() + SysDeptServiceImpl.SEPARATOR + sysAclDto.getAclModuleId() + '%';
		map.put("moduleLevel", formatLevel);// 特殊模糊查询，查询自己和自己子权限模块的权限点
		PageResult<SysAclDto> result = new PageResult<>(sysAclMapper.selectAll(map), sysAclMapper.countAll(map));
		return result;
	}

}
