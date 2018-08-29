package top.ruandb.service;

import top.ruandb.dto.PageQuery;
import top.ruandb.dto.PageResult;
import top.ruandb.dto.SysAclDto;
import top.ruandb.entity.SysAcl;

public interface SysAclServiceI {

	public void addAcl(SysAcl sysAcl);

	public void updateAcl(SysAcl sysAcl);

	public PageResult<SysAclDto> selectAll(SysAclDto sysAclDto, PageQuery pq); // 分页查询所有用户
}
