package top.ruandb.dao;

import java.util.List;

import top.ruandb.dto.PageQuery;
import top.ruandb.entity.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    int countByPhone(SysUser record);
    
    int countByMail(SysUser record);
    
	public List<SysUser> selectAll(PageQuery pq);
	
	public int countAll(PageQuery pq);
	
	public SysUser findByKeyWord(String keyWord);
}