package top.ruandb.dao;

import top.ruandb.entity.SyaDept;

public interface SyaDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SyaDept record);

    int insertSelective(SyaDept record);

    SyaDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SyaDept record);

    int updateByPrimaryKey(SyaDept record);
}