package top.ruandb.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	//返回后台主页
	@RequestMapping("/main.page")
	public String mingPage(){
		return "main" ;
	}
}
