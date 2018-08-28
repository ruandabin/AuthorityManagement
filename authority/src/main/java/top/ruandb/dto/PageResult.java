package top.ruandb.dto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@Builder
public class PageResult<T> {
	
	public PageResult() {
	}
	public PageResult(List<T> rows, int total) {
		super();
		this.rows = rows;
		this.total = total;
	}
	private List<T> rows;
	private int total ;
}
