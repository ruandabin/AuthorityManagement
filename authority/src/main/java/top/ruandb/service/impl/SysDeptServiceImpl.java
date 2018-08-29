package top.ruandb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import top.ruandb.common.RequestHolder;
import top.ruandb.dao.SysDeptMapper;
import top.ruandb.dto.SysDeptDto;
import top.ruandb.entity.SysDept;
import top.ruandb.exception.AuthorityException;
import top.ruandb.service.SysDeptServiceI;
import top.ruandb.utils.BeanValidator;
import top.ruandb.utils.IpUtil;

@Service("sysdeptService")
public class SysDeptServiceImpl implements SysDeptServiceI {
	// 部门层级连接符
	public final static String SEPARATOR = "-";
	// 顶层层级
	public final static String ROOT = "0";

	@Autowired
	private SysDeptMapper sysdeptMapper;

	// 添加部门
	@Override
	public void addSysDept(SysDept sysdept) {
		BeanValidator.check(sysdept);
		if (checkExist(sysdept)) {
			throw new AuthorityException("同层级下已经存在相同名称的部门");
		}
		sysdept.setLevel(calculateLevel(sysdept));
		sysdept.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysdept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));// TODO
		sysdept.setOperateTime(new Date());
		sysdeptMapper.insertSelective(sysdept);
	}

	//更新部门
	@Override
	public void updateSysDept(SysDept sysdept) {
		BeanValidator.check(sysdept);
		SysDept beforDept = sysdeptMapper.selectByPrimaryKey(sysdept.getId());
		Preconditions.checkNotNull(beforDept, "待更新的部门不存在");
		if (checkExist(sysdept)) {
			throw new AuthorityException("同层级下已经存在相同名称的部门");
		}
		
		sysdept.setLevel(calculateLevel(sysdept));
		sysdept.setOperator(RequestHolder.getCurrentUser().getUsername());// TODO
		sysdept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));// TODO
		sysdept.setOperateTime(new Date());
		updateWithChild(beforDept,sysdept);
	}
	//更新子部门
	private void updateWithChild(SysDept before,SysDept after){
		sysdeptMapper.updateByPrimaryKeySelective(after);
		if( !before.getLevel().equals(after.getLevel()) ){
			List<SysDept> beforeChild = sysdeptMapper.selectAllChildDept(before);
			if(CollectionUtils.isNotEmpty(beforeChild)){
				for(SysDept dept : beforeChild){
					if(dept.getLevel().indexOf(before.getLevel()) == 0){
						dept.setLevel(after.getLevel().concat(dept.getLevel().substring(before.getLevel().length())));
						sysdeptMapper.updateByPrimaryKeySelective(dept);
					}
				}
			}
		}
	}
	// 检查部门是否已经存在
	private boolean checkExist(SysDept sysdept) {
		int count = sysdeptMapper.countByNameAndParentId(sysdept);
		return count > 0 ? true : false;
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
			return StringUtils.join(parentLevel, SEPARATOR,
					sysdept.getParentId());
		} else {
			return ROOT;
		}
	}

	// 获取部门树
	@Override
	public List<SysDeptDto> deptTree() {
		List<SysDeptDto> dtoList = Lists.newArrayList();
		List<SysDept> detpList = sysdeptMapper.selectAllDept();
		for (SysDept dept : detpList) {
			dtoList.add(SysDeptDto.adapt(dept));
		}
		return list2Tree(dtoList);
	}

	// 部门列表转换成部门树
	private List<SysDeptDto> list2Tree(List<SysDeptDto> dtoList) {
		// Multimap:<key,ArrayList<>>
		Multimap<String, SysDeptDto> multimap = ArrayListMultimap.create();
		List<SysDeptDto> rootList = Lists.newArrayList();
		for (SysDeptDto deptDto : dtoList) {
			multimap.put(deptDto.getLevel(), deptDto);
			// 获取最顶层的部门
			if (deptDto.getLevel() != null && deptDto.getLevel().equals(ROOT)) {
				rootList.add(deptDto);
			}
		}
		// 排序
		rootList = rootList.stream()
				.sorted((e1, e2) -> e1.getSeq() - e2.getSeq())
				.collect(Collectors.toList());
		transform2Tree(rootList, multimap);
		return rootList;
	}

	/**
	 * 递归转换成树list，每次递归的动作：遍历父亲，找到儿子存放在父亲的list中
	 * 
	 * @param fList
	 *            父层list表，从顶层开始
	 * @param sLevel
	 *            子层 level
	 * @param multimap
	 *            全部数据 level -> list<SysDeptDto>
	 */
	private void transform2Tree(List<SysDeptDto> fList,
			Multimap<String, SysDeptDto> multimap) {
		for (int i = 0; i < fList.size(); i++) {
			SysDeptDto deptDto = fList.get(i);
			String sunLevel = deptDto.getLevel().concat(SEPARATOR)
					.concat(deptDto.getId().toString());
			List<SysDeptDto> sList = (List<SysDeptDto>) multimap.get(sunLevel);
			if (CollectionUtils.isNotEmpty(sList)) {
				sList = sList.stream()
						.sorted((w1, w2) -> w1.getSeq() - w2.getSeq())
						.collect(Collectors.toList());
				fList.get(i).setChildren(sList);
				transform2Tree(sList, multimap);
			}
		}
	}
}
