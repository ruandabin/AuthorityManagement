package top.ruandb.dto;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;
import top.ruandb.entity.SysAcl;

@Getter
@Setter
public class SysAclDto extends SysAcl{

	private String moduleName;
	private String moduleLevel;
	
	//当前节点是否被选中
	private boolean checked;
	
	//是否有操作当前节点的权限
	private boolean hasAcl;
	
	public static SysAclDto adapt(SysAcl sysAcl) {
		SysAclDto sysAclDto = new SysAclDto();
		BeanUtils.copyProperties(sysAcl, sysAclDto);
		return sysAclDto;
	}
}
