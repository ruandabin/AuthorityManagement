package top.ruandb.dto;

import lombok.Getter;
import lombok.Setter;
import top.ruandb.entity.SysUser;

@Getter
@Setter
public class SysUserDto extends SysUser{

	private String deptName;
}
