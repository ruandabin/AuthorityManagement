package top.ruandb.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.ruandb.entity.SysDept;

/**
 * Sysdept 适配模型
 * @author rdb
 *
 */
@Getter
@Setter
@ToString
public class SysDeptDto extends SysDept{

	private List<SysDeptDto> children = Lists.newArrayList();
	private String text ;
	
	public static SysDeptDto adapt(SysDept dept){
		SysDeptDto deptDto = new SysDeptDto();
		deptDto.text = dept.getName() ;
		BeanUtils.copyProperties(dept, deptDto);
		return deptDto ;
	}
}
