package top.ruandb.service;

import top.ruandb.dto.PageQuery;
import top.ruandb.dto.PageResult;
import top.ruandb.dto.SysUserDto;
import top.ruandb.entity.SysUser;

public interface SysUserServiceI {

	public void addUser(SysUser sysUser);
	
	public void updateUser(SysUser sysUser);
	
	public SysUser findByKeyWord(String keyWord);
	
	public PageResult<SysUserDto> selectAll(SysUserDto sysUserDto ,PageQuery pq); //分页查询所有用户
}
