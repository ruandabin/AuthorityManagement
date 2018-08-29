package top.ruandb.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;

import top.ruandb.entity.SysAclModule;
@Getter
@Setter
public class SysAclModuleDto extends SysAclModule {

	private List<SysAclModuleDto> children = Lists.newArrayList();
	private String text;

	public static SysAclModuleDto adapt(SysAclModule sysAclModule) {
		SysAclModuleDto sysAclModuleDto = new SysAclModuleDto();
		sysAclModuleDto.text = sysAclModule.getName();
		BeanUtils.copyProperties(sysAclModule, sysAclModuleDto);
		return sysAclModuleDto;
	}
}
