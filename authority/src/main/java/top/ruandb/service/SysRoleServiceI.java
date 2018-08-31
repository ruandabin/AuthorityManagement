package top.ruandb.service;
import java.util.List;
import top.ruandb.dto.SysRoleDto;
import top.ruandb.entity.SysRole;

public interface SysRoleServiceI {
	
	public void addSysRole(SysRole sysRole);
	
	public void updateSysRole(SysRole sysRole);
	
	public List<SysRoleDto> listRole();
	
	public List<SysRoleDto> roleAclTree(int roleId);
}
