package top.ruandb.entity;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SysRole {
	private Integer id;

	@NotBlank(message = "角色名称不可以为空")
	@Length(min = 2, max = 20, message = "角色名称长度需要在2-20个字之间")
	private String name;

	@Min(value = 1, message = "角色类型不合法")
	@Max(value = 2, message = "角色类型不合法")
	private Integer type;

	@NotNull(message = "角色状态不可以为空")
	@Min(value = 0, message = "角色状态不合法")
	@Max(value = 1, message = "角色状态不合法")
	private Integer status;

	@Length(min = 0, max = 200, message = "角色备注长度需要在200个字符以内")
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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