package top.ruandb.entity;

import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SysAcl {
	private Integer id;

	private String code;
	@NotBlank(message = "权限点名称不可以为空")
	@Length(min = 2, max = 20, message = "权限点名称长度需要在2-20个字之间")
	private String name;

	@NotNull(message = "必须指定权限模块")
	private Integer aclModuleId;

	@Length(min = 6, max = 100, message = "权限点URL长度需要在6-100个字符之间")
	private String url;
	@NotNull(message = "必须指定权限点的类型")
	@Min(value = 1, message = "权限点类型不合法")
	@Max(value = 3, message = "权限点类型不合法")
	private Integer type;
	@NotNull(message = "必须指定权限点的状态")
	@Min(value = 0, message = "权限点状态不合法")
	@Max(value = 1, message = "权限点状态不合法")
	private Integer status;
	@NotNull(message = "必须指定权限点的展示顺序")
	private Integer seq;
	@Length(min = 0, max = 200, message = "权限点备注长度需要在200个字符以内")
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getAclModuleId() {
		return aclModuleId;
	}

	public void setAclModuleId(Integer aclModuleId) {
		this.aclModuleId = aclModuleId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
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

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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