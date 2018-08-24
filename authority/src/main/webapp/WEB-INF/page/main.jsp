<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="header.jsp" %>
<title>权限管理</title>
<script type="text/javascript"></script>
</head>
<body class="easyui-layout" >
<div class="rdb" region="north" style="height: 85px "></div>
<div region="center"></div>
<div region="west" style="width: 200px" title="部门列表" split="true">
<ul id="tt"></ul>
</div>
<div region="south" style="height: 25px;padding: 5px;" align="center"></div>
<script type="text/javascript">
$('#tt').tree({
    url: "${pageContext.request.contextPath}/sys/dept/tree.data",
    loadFilter: function(data){
		if (data.ret){
			return data.data;
		}
    }
});
</script>
</body>
</html>