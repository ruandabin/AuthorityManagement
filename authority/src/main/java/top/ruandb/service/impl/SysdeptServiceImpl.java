package top.ruandb.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import top.ruandb.dao.SysDeptMapper;
import top.ruandb.dto.SysDeptDto;
import top.ruandb.entity.SysDept;
import top.ruandb.exception.AuthorityException;
import top.ruandb.service.SysdeptServiceI;
import top.ruandb.utils.BeanValidator;

@Service("sysdeptService")
public class SysdeptServiceImpl implements SysdeptServiceI {
	//部门层级连接符
	public final static String SEPARATOR = "-";
	//顶层层级
	public final static String ROOT = "0";
	
	@Autowired
	private SysDeptMapper sysdeptMapper;

	//添加部门
	@Override
	public void addSysDept(SysDept sysdept) {
		BeanValidator.check(sysdept);
		if (checkExist(sysdept)) {
			throw new AuthorityException("同层级下已经存在相同名称的部门");
		}
		sysdept.setLevel(calculateLevel(sysdept));
		sysdept.setOperator("admin");//TODO
		sysdept.setOperateIp("127.0.0.1");//TODO
		sysdept.setOperateTime(new Date());
		sysdeptMapper.insertSelective(sysdept);
	}

	// 检查部门是否已经存在
	private boolean checkExist(SysDept sysdept) {
		int count = sysdeptMapper.countByNameAndParentId(sysdept);
		return count>0? true : false ;
	}

	// 计算部门存级
	@SuppressWarnings("unchecked")
	private String calculateLevel(SysDept sysdept) {
		if (sysdept == null) {
			return null;
		}
		if (sysdept.getParentId() != null && sysdept.getParentId() != 0) {
			String parentLevel = sysdeptMapper.selectByPrimaryKey(
					sysdept.getParentId()).getLevel();
			return StringUtils.join(parentLevel, SEPARATOR, sysdept.getParentId());
		} else {
			return ROOT ;
		}
	}

	//获取部门树
	@Override
	public List<SysDeptDto> deptTree(SysDept sysdept) {
		List<SysDeptDto> dtoList = Lists.newArrayList();
		List<SysDept> detpList = sysdeptMapper.selectAllDept(sysdept);
		for(SysDept dept : detpList){
			dtoList.add(SysDeptDto.adapt(dept));
		}
		return list2Tree(dtoList);
	}
	
	//部门列表转换成部门树
	private List<SysDeptDto> list2Tree(List<SysDeptDto> dtoList){
		
		return null ;
	}
}
