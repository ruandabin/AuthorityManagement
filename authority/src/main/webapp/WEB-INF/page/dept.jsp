<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="header.jsp"%>
<title>用户管理</title>
<script type="text/javascript"></script>
</head>
<body class="easyui-layout" fit="true">
	<div region="north" style="height: 40px" border="false">
		用户管理——>维护部门与用户关系</div>
	<div region="center" border="false">
		<table id="dept_dg" title="用户列表" toolbar="#tb" fit="true"></table>
		<div id="tb">
			<div>
				&nbsp;姓名：&nbsp;<input type="text" id="dept_username" size="20"
					onkeydown="if(event.keyCode==13) searchJob()" /> <a
					href="javascript:searchUser()" class="easyui-linkbutton"
					iconCls="icon-search" plain="true">搜索</a>
			</div>
		</div>
	</div>
	<div region="west" style="width: 200px" title="" split="false"
		border="false">
		<div id="dept_panel" class="easyui-panel" title="部门列表"
			data-options="fit:true,tools:'#tt'">
			<ul id="dept_tt"></ul>
		</div>
		<div id="tt">
			<a href="javascript:void(0)" class="icon-add"
				onclick="javascript:openDeptAddDialog()"></a> <a
				href="javascript:void(0)" class="icon-edit"
				onclick="javascript:openDeptUpdateDialog()"></a>
		</div>
	</div>

	<div id="dept_dlg" class="easyui-dialog" modal="true"
		style="width: 500px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="10px" border="0">
				<tr>
					<td>上级部门：</td>
					<td><input id="dept_cc" name="parentId" style="width: 200px;">
					</td>
				</tr>
				<tr>
					<td>名称：</td>
					<td><input class="easyui-validatebox" type="text"
						id="dept_name" name="name" style="width: 200px;" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>顺序：</td>
					<td><input class="easyui-validatebox" type="text"
						id="dept_seq" name="seq" style="width: 200px;" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input class="easyui-validatebox" type="text"
						id="dept_remark" name="remark" style="width: 200px;" /></td>
				</tr>

			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:saveDept()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a href="javascript:closeDeptDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	<script type="text/javascript">
		var url;

		function openDeptAddDialog() {
			resetValue();
			$("#dept_dlg").dialog("open").dialog("setTitle", "添加部门");
			url = "${pageContext.request.contextPath}/sys/dept/save.data";
		}

		function openDeptUpdateDialog() {
			var selectedRows = $("#dept_tt").tree('getSelected');
			if (selectedRows == null) {
				$.messager.alert("系统提示", "请选择一条要编辑的数据！");
				return;
			}
			$("#dept_dlg").dialog("open").dialog("setTitle", "编辑部门信息");
			$('#fm').form('load', selectedRows);
			url = "${pageContext.request.contextPath}/sys/dept/update.data?id="
					+ selectedRows.id;
		}

		function resetValue() {
			$("#dept_cc").combotree('setValue', "");
			$("#dept_name").val("");
			$("#dept_seq").val("");
			$("#dept_remark").val("");
		}

		function saveDept() {
			$("#fm").form("submit", {
				url : url,
				onSubmit : function() {
					return $(this).form("validate");
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.ret) {
						$.messager.alert("系统提示", "保存成功");
						resetValue();
						$("#dept_dlg").dialog("close");
						$("#dept_tt").tree("reload");
					} else {
						$.messager.alert("系统提示", "保存失败:" + result.msg);
						return;
					}
				}
			});
		}

		function closeDeptDialog() {
			$("#dept_dlg").dialog('close');
			resetValue();
		}
		
		function searchUser(){
			$('#dept_dg').datagrid('reload',{
				username:$("#dept_username").val()
			});
		}
		$('#dept_cc').combotree({
			url : '${pageContext.request.contextPath}/sys/dept/tree.data',
			loadFilter : function(data) {
				if (data.ret) {
					return data.data;
				}
			}
		});

		$('#dept_tt').tree({
			url : "${pageContext.request.contextPath}/sys/dept/tree.data",
			//checkbox:true,
			loadFilter : function(data) {
				if (data.ret) {
					return data.data;
				}
			}
		});

		$('#dept_dg').datagrid({
			fitColumns : true,
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			fit : true,
			url : "${pageContext.request.contextPath}/sys/user/selectAll.data",
			columns : [ [ {
				field : 'id',
				width : 50,
				align : 'center',
				title : '编号',
				hidden:true
			}, {
				field : 'username',
				width : 150,
				align : 'center',
				title : '姓名'
			}, {
				field : 'deptName',
				width : 60,
				align : 'center',
				title : '所属部门'
			}, {
				field : 'mail',
				width : 100,
				align : 'center',
				title : '电子邮箱'
			}, {
				field : 'phone',
				width : 150,
				align : 'center',
				title : '电话'
			}, {
				field : 'status',
				width : 60,
				align : 'center',
				title : '状态',
				formatter: function(value,row,index){
					if(value == 1){
						return "正常";
					}else if(value == 0){
						return "冻结";
					}else{
						return value;
					}
				}
			}

			] ],
			loadFilter : function(data) {
				if (data.ret) {
					return data.data;
				} else {
					return;
				}
			}
		});
	</script>
</body>
</html>