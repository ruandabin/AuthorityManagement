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
					onkeydown="if(event.keyCode==13) searchJob()" /> 
					<input type="hidden" id="dept_deptId" size="20"/> 
					<input type="hidden" id="dept_level" size="20"/> 
					<a
					href="javascript:searchUser()" class="easyui-linkbutton"
					iconCls="icon-search" plain="true">搜索</a> <a
					href="javascript:openUserAddDialog()" class="easyui-linkbutton"
					iconCls="icon-add" plain="true">添加</a> <a
					href="javascript:openUserUpdateDialog()" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true">修改</a> 
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

	<div id="sysUser_dlg" class="easyui-dialog" modal="true"
		style="width: 500px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#user-dlg-buttons">
		<form id="user_fm" method="post">
			<table cellspacing="10px" border="0">
				<tr>
					<td>所属部门：</td>
					<td><input id="user_cc" name="deptId" values="id" style="width: 200px;">&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td><input class="easyui-validatebox" type="text"
						id="user_username" name="username" style="width: 200px;" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>电子邮箱：</td>
					<td><input class="easyui-validatebox" type="text" id="user_mail"
						name="mail" style="width: 200px;" />&nbsp;<font color="red">*</font></td>
				</tr>
				<tr>
					<td>电话：</td>
					<td><input class="easyui-validatebox" type="text"
						id="user_phone" name="phone" style="width: 200px;" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select id="user_co" class="easyui-combobox" name="status"
						style="width: 200px;">
						<option value="1">有效</option>
						<option value="0">无效</option>
						<option value="2">删除</option>
					</select></td>
					
				</tr>
				<tr>
					<td><label for="userRemark">备注：</label></td>
					<td><textarea name="remark" id="userRemark"
							 rows="3" cols="25"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="user-dlg-buttons">
		<a href="javascript:saveUser()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a href="javascript:closeUserDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	<script type="text/javascript">
		var url;

		function openUserAddDialog() {
			resetUserValue()
			$("#sysUser_dlg").dialog("open").dialog("setTitle", "添加用户");
			url = "${pageContext.request.contextPath}/sys/user/save.data";
		}
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
		
		function openUserUpdateDialog() {
			var selectedRows=$("#dept_dg").datagrid('getSelections');
			if(selectedRows.length!=1){
				$.messager.alert("系统提示","请选择一条要编辑的数据！");
				return;
			}
			var row=selectedRows[0];
			$("#sysUser_dlg").dialog("open").dialog("setTitle","编辑用户信息");
			$('#user_fm').form('load',row);
			url="${pageContext.request.contextPath}/sys/user/update.data?id="+row.id;
		}

		function resetValue() {
			$("#dept_cc").combotree('setValue', "");
			$("#dept_name").val("");
			$("#dept_seq").val("");
			$("#dept_remark").val("");
		}

		function resetUserValue() {
			$("#user_cc").combotree('setValue', "");
			$("#user_username").val("");
			$("#user_mail").val("");
			$("#user_phone").val("");
			$("#user_co").combobox('setValues', "1");
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
						resetUserValue();
						$("#sysUser_dlg").dialog("close");
						$("#dept_tt").tree("reload");
					} else {
						$.messager.alert("系统提示", "保存失败:" + result.msg);
						return;
					}
				}
			});
		}
		
		function saveUser() {
			$("#user_fm").form("submit", {
				url : url,
				onSubmit : function() {
					return $(this).form("validate");
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.ret) {
						$.messager.alert("系统提示", "保存成功");
						resetValue();
						$("#sysUser_dlg").dialog("close");
						$("#dept_dg").datagrid("reload");
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
		function closeUserDialog() {
			$("#sysUser_dlg").dialog('close');
			resetUserValue();
		}

		function searchUser() {
			$('#dept_dg').datagrid('reload', {
				username : $("#dept_username").val(),
				deptLevel:$("#dept_level").val(),
				deptId:$("#dept_deptId").val()
				
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

		$('#user_cc').combotree({
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
			},
			onClick: function(node){
				$("#dept_level").val(node.level);
				$("#dept_deptId").val(node.id);
				searchUser(); 
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
				hidden : true
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
				formatter : function(value, row, index) {
					if (value == 1) {
						return "正常";
					} else if (value == 0) {
						return "冻结";
					} else {
						return "删除";
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