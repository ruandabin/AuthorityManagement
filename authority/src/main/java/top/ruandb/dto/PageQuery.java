package top.ruandb.dto;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

public class PageQuery {

	@Getter
	@Setter
	@Min(value = 1, message = "当前页码不合法")
	private int page;// 当前页码

	@Getter
	@Setter
	@Min(value = 1, message = "每页展示数量不合法")
	private int rows;// 每页记录数

	@Setter
	private int start;// 起始页

	public int getStart() {
		return start = (page - 1) * rows;
	}

}
