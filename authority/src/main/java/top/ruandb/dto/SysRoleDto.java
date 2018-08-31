package top.ruandb.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;

import com.google.common.collect.Lists;

import top.ruandb.entity.SysRole;
@Getter
@Setter
public class SysRoleDto extends SysRole{

	private List<SysRoleDto> children = Lists.newArrayList();
	private String text;
	
	public static SysRoleDto adapt(SysRole sysRole){
		SysRoleDto sysRoleDto = new SysRoleDto();
		sysRoleDto.text = sysRole.getName();
		BeanUtils.copyProperties(sysRole, sysRoleDto);
		return sysRoleDto ;
	}
}
