package top.ruandb.service;

import java.util.List;

import top.ruandb.dto.PageQuery;
import top.ruandb.dto.PageResult;
import top.ruandb.entity.SysUser;

public interface SysUserServiceI {

	public void addUser(SysUser sysUser);
	
	public void updateUser(SysUser sysUser);
	
	public SysUser findByKeyWord(String keyWord);
	
	public PageResult<SysUser> selectAll(PageQuery pq); //分页查询所有用户
}
