package top.ruandb.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

import top.ruandb.common.JsonData;
import top.ruandb.utils.BeanValidator;


@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/test")
	@ResponseBody
	public JsonData test(){
		return  JsonData.success("test controller", "test");
	}
	
	@RequestMapping("/main")
	public String mainTest(){
		return "main";
	}
}
