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
<div  region="north" style="height: 85px ">
<table style="padding: 5px" width="100%">
		<tr>
			<td width="50%">
				<!-- <div class="knockout"><a>ETL监控分析系统</a></div> -->
			</td>
			<td valign="bottom" align="right" width="50%">
				<font size="3">&nbsp;&nbsp;<strong>欢迎：</strong>${user.username}</font>
				<div id="result"></div>
			</td>
		</tr>
	</table>
</div>
<div region="center">
	<div class="easyui-tabs" fit="true" border="false" id="tabs">
		<div  title="首页" data-options="iconCls:'icon-home'">
			<iframe frameborder=0 scrolling='auto' style='width:100%;height:98%' src='${pageContext.request.contextPath}/sys/dept/dept.page'></iframe>
		</div>
	</div>
</div>
<div region="west" style="width: 200px" title="导航菜单" split="true">
	<div class="easyui-accordion" data-options="fit:true,border:false">
		<div title="权限管理" data-options="iconCls:'icon-yxgl',selected:'false'" style="padding: 10px">
			<a href="javascript:openTab('用户管理','dept/dept.page','icon-yxjhgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-yxjhgl'" style="width: 150px;">用户管理</a>
			<a href="javascript:openTab('角色管理','role/role.page','icon-khkfjh')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khkfjh'" style="width: 150px;">角色管理</a>
			<a href="javascript:openTab('权限管理','aclModule/acl.page','icon-khkfjh')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khkfjh'" style="width: 150px;">权限管理</a>
			<a href="javascript:openTab('权限更新记录','log.jsp','icon-khkfjh')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khkfjh'" style="width: 150px;">权限更新记录</a>
		</div>
		<div title="系统管理"  data-options="iconCls:'icon-item'" style="padding:10px">
			<a href="javascript:openPasswordModifyDialog()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-modifyPassword'" style="width: 150px;">修改密码</a>
			<a href="javascript:logout()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'" style="width: 150px;">安全退出</a>
		</div>
	</div>
</div>
<div region="south" style="height: 25px;padding: 5px;" align="center"></div>

<script type="text/javascript">
var url;
function openTab(text,url,iconCls){
	if($("#tabs").tabs("exists",text)){
		$("#tabs").tabs("select",text);
	}else{
		var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:98%' src='${pageContext.request.contextPath}/sys/"+url+"'></iframe>";
		$("#tabs").tabs("add",{
			title:text,
			iconCls:iconCls,
			closable:true,
			content:content
		});
	}
}

function logout(){
	$.messager.confirm("系统提示","您确定要退出系统吗",function(r){
		if(r){
			window.location.href="${pageContext.request.contextPath}/logout.page";
		}
	});
}
</script>
</body>
</html>