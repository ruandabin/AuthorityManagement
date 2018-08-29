package top.ruandb.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import top.ruandb.common.RequestHolder;
import top.ruandb.dao.SysUserMapper;
import top.ruandb.dto.Mail;
import top.ruandb.dto.PageQuery;
import top.ruandb.dto.PageResult;
import top.ruandb.dto.SysUserDto;
import top.ruandb.entity.SysUser;
import top.ruandb.exception.ParamException;
import top.ruandb.service.SysUserServiceI;
import top.ruandb.utils.BeanValidator;
import top.ruandb.utils.IpUtil;
import top.ruandb.utils.MD5Util;
import top.ruandb.utils.MailUtil;
import top.ruandb.utils.PasswordUtils;
import top.ruandb.utils.StringUtil;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserServiceI {

	@Autowired
	private SysUserMapper sysUserMapper;

	// 增加用户
	@Override
	public void addUser(SysUser sysUser) {
		BeanValidator.check(sysUser);
		if (checkPhoneExists(sysUser)) {
			throw new ParamException("电话已被占用");
		}
		if (checkMailExists(sysUser)) {
			throw new ParamException("邮箱已被占用");
		}
		String password = PasswordUtils.randomPassword();
		 password = "12345678";// 测试使用
		String encryptedPassword = MD5Util.encrypt(password);
		sysUser.setPassword(encryptedPassword);
		sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder
				.getCurrentRequest()));// TODO
		sysUser.setOperateTime(new Date());
		// TODO: sendEmail
		Set<String> receiver = Sets.newHashSet();
//		receiver.add(" ");// TODO
//		Mail mail = Mail.builder().subject("权限管理系统密码").receivers(receiver)
//				.message("欢迎使用权限管理系统，您的登录名是您注册的邮箱或手机号,密码：" + password).build();
//		MailUtil.send(mail);
		sysUserMapper.insertSelective(sysUser);
	}

	// 更新用户信息
	@Override
	public void updateUser(SysUser sysUser) {
		BeanValidator.check(sysUser);
		if (checkPhoneExists(sysUser)) {
			throw new ParamException("电话已被占用");
		}
		if (checkMailExists(sysUser)) {
			throw new ParamException("邮箱已被占用");
		}
		SysUser before = sysUserMapper.selectByPrimaryKey(sysUser.getId());
		Preconditions.checkNotNull(before, "待更新的用户不存在");
		sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());// TODO
		sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder
				.getCurrentRequest()));// TODO
		sysUser.setOperateTime(new Date());
		sysUserMapper.updateByPrimaryKeySelective(sysUser);
	}

	// 检查新增用户的电话是否已被使用
	private boolean checkPhoneExists(SysUser sysUser) {
		return sysUserMapper.countByPhone(sysUser) > 0;
	}

	// 检查新增用户的邮箱是否已被使用
	private boolean checkMailExists(SysUser sysUser) {
		return sysUserMapper.countByMail(sysUser) > 0;
	}

	public List<SysUser> getAll() {
		return null;
	}

	@Override
	public SysUser findByKeyWord(String keyWord) {
		return sysUserMapper.findByKeyWord(keyWord);
	}

	// 查询所有user
	@Override
	public PageResult<SysUserDto> selectAll(SysUserDto sysUserDto, PageQuery pq) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("start", pq.getStart());
		map.put("rows", pq.getRows());
		map.put("username", StringUtil.formatLike(sysUserDto.getUsername()));
		map.put("deptId", sysUserDto.getDeptId());
		String formatLevel = sysUserDto.getDeptLevel() == null? null : sysUserDto.getDeptLevel()
				+ SysDeptServiceImpl.SEPARATOR + sysUserDto.getDeptId() + '%' ; 
		map.put("deptLevel", formatLevel);// 特殊模糊查询，查询自己和自己子部门的用户
		PageResult<SysUserDto> result = new PageResult<SysUserDto>(
				sysUserMapper.selectAll(map), sysUserMapper.countAll(map));
		return result;
	}
}
