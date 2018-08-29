package top.ruandb.dto;

import lombok.Getter;
import lombok.Setter;
import top.ruandb.entity.SysAcl;

@Getter
@Setter
public class SysAclDto extends SysAcl{

	private String moduleName;
	private String moduleLevel;
}
