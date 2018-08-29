package top.ruandb.entity;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SysAclModule {
	private Integer id;

	@NotBlank(message = "权限模块名称不可以为空")
	@Length(min = 2, max = 20, message = "权限模块名称长度需要在2~20个字之间")
	private String name;

	private Integer parentId;

	private String level;

	@NotNull(message = "权限模块展示顺序不可以为空")
	private Integer seq;

	@NotNull(message = "权限模块状态不可以为空")
	@Min(value = 0, message = "权限模块状态不合法")
	@Max(value = 1, message = "权限模块状态不合法")
	private Integer status;

	@Length(max = 200, message = "权限模块备注需要在200个字之间")
	private String remark;

	private String operator;

	private Date operateTime;

	private String operateIp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator == null ? null : operator.trim();
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateIp() {
		return operateIp;
	}

	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp == null ? null : operateIp.trim();
	}
}