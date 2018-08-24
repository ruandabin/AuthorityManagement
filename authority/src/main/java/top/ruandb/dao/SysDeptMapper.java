package top.ruandb.dao;

import java.util.List;

import top.ruandb.entity.SysDept;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);
    
    int countByNameAndParentId(SysDept record);
    
    List<SysDept> selectAllDept();
    
    List<SysDept> selectAllChildDept(SysDept record);
}