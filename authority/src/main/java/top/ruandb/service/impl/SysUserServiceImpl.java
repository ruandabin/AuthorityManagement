package top.ruandb.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import top.ruandb.dao.SysUserMapper;
import top.ruandb.dto.PageQuery;
import top.ruandb.dto.PageResult;
import top.ruandb.entity.SysUser;
import top.ruandb.exception.ParamException;
import top.ruandb.service.SysUserServiceI;
import top.ruandb.utils.BeanValidator;
import top.ruandb.utils.MD5Util;
import top.ruandb.utils.PasswordUtils;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserServiceI {

	@Autowired
	private SysUserMapper sysUserMapper;

	//增加用户
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
		sysUser.setOperator("admin");// TODO
		sysUser.setOperateIp("127.0.0.1");// TODO
		sysUser.setOperateTime(new Date());
		// TODO: sendEmail
		sysUserMapper.insertSelective(sysUser);
	}

	//更新用户信息
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
		Preconditions.checkNotNull(before,"待更新的用户不存在");
		sysUser.setOperator("admin");// TODO
		sysUser.setOperateIp("127.0.0.1");// TODO
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

	public List<SysUser> getAll(){
		return null ;
	}

	@Override
	public SysUser findByKeyWord(String keyWord) {
		return sysUserMapper.findByKeyWord(keyWord);
	}

	//查询所有user
	@Override
	public PageResult<SysUser> selectAll(PageQuery pq) {
		PageResult<SysUser> result = new PageResult<SysUser>(sysUserMapper.selectAll(pq), sysUserMapper.countAll(pq));
		return result;
	}
}
