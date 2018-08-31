<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="header.jsp"%>
<title>权限管理</title>
<script type="text/javascript"></script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false">
		<table id="acl_dg" title="权限列表" toolbar="#tb" fit="true"></table>
		<div id="tb">
			<div>
				&nbsp;权限名称：&nbsp;<input type="text" id="acl_name" size="20"
					onkeydown="if(event.keyCode==13) searchJob()" /> <input
					type="hidden" id="acl_moduleId" size="20" /> <input type="hidden"
					id="acl_level" size="20" /> <a href="javascript:searchAcl()"
					class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
				<a href="javascript:openAclAddDialog()" class="easyui-linkbutton"
					iconCls="icon-add" plain="true">添加</a> <a
					href="javascript:openAclUpdateDialog()" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true">修改</a>
			</div>

		</div>
	</div>
	<div region="west" style="width: 200px" title="" split="false"
		border="false">
		<div id="aclMoudle_panel" class="easyui-panel" title="权限模块列表"
			data-options="fit:true,tools:'#tt'">
			<ul id="aclModule_tt"></ul>
		</div>
		<div id="tt">
			<a href="javascript:void(0)" class="icon-add"
				onclick="javascript:openAclModuleAddDialog()"></a> <a
				href="javascript:void(0)" class="icon-edit"
				onclick="javascript:openAclModuleUpdateDialog()"></a>
		</div>
	</div>

	<div id="aclModule_dlg" class="easyui-dialog" modal="true"
		style="width: 500px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="10px" border="0">
				<tr>
					<td>上级模块：</td>
					<td><input id="aclModule_cc" name="parentId"
						style="width: 200px;"></td>
				</tr>
				<tr>
					<td>名称：</td>
					<td><input class="easyui-validatebox" type="text"
						id="aclModule_name" name="name" style="width: 200px;" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>顺序：</td>
					<td><input class="easyui-validatebox" type="text"
						id="aclModule_seq" name="seq" style="width: 200px;" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select id="aclModule_co" class="easyui-combobox"
						name="status" style="width: 200px;">
							<option value="1">正常</option>
							<option value="0">冻结</option>
					</select></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input class="easyui-validatebox" type="text"
						id="aclModule_remark" name="remark" style="width: 200px;" /></td>
				</tr>

			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:saveAclModule()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a href="javascript:closeAclModuleDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>

	<div id="sysAcl_dlg" class="easyui-dialog" modal="true"
		style="width: 500px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#acl-dlg-buttons">
		<form id="acl_fm" method="post">
			<table cellspacing="10px" border="0">
				<tr>
					<td>权限模块：</td>
					<td><input id="acl_cc" name=aclModuleId values="id"
						style="width: 200px;">&nbsp;<font color="red">*</font></td>
				</tr>
				<tr>
					<td>权限名称：</td>
					<td><input class="easyui-validatebox" type="text"
						id="acl_name" name="name" style="width: 200px;" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>顺序：</td>
					<td><input class="easyui-validatebox" type="text" id="acl_seq"
						name="seq" style="width: 200px;" />&nbsp;<font color="red">*</font></td>
				</tr>
				<tr>
					<td>URL：</td>
					<td><input class="easyui-validatebox" type="text" id="acl_url"
						name="url" style="width: 200px;" />&nbsp;<font color="red">*</font></td>
				</tr>
				<tr>
					<td>类型：</td>
					<td><select id="acl_type" class="easyui-combobox" name="type"
						style="width: 200px;">
							<option value="1">菜单</option>
							<option value="2">按钮</option>
							<option value="2">其他</option>
					</select></td>

				</tr>
				<tr>
					<td>状态：</td>
					<td><select id="acl_co" class="easyui-combobox" name="status"
						style="width: 200px;">
							<option value="1">有效</option>
							<option value="0">无效</option>
							<option value="2">删除</option>
					</select></td>

				</tr>
				<tr>
					<td><label for="userRemark">备注：</label></td>
					<td><textarea name="remark" id="userRemark" rows="3" cols="25"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="acl-dlg-buttons">
		<a href="javascript:saveAcl()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a href="javascript:closeAclDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	<script type="text/javascript">
		var url;

		function openAclAddDialog() {
			resetAclValue()
			$("#sysAcl_dlg").dialog("open").dialog("setTitle", "添加权限点");
			url = "${pageContext.request.contextPath}/sys/acl/save.data";
		}
		function openAclModuleAddDialog() {
			resetValue();
			$("#aclModule_dlg").dialog("open").dialog("setTitle", "添加权限模块");
			url = "${pageContext.request.contextPath}/sys/aclModule/save.data";
		}

		function openAclModuleUpdateDialog() {
			var selectedRows = $("#aclModule_tt").tree('getSelected');
			if (selectedRows == null) {
				$.messager.alert("系统提示", "请选择一条要编辑的数据！");
				return;
			}
			$("#aclModule_dlg").dialog("open").dialog("setTitle", "编辑部门信息");
			$('#fm').form('load', selectedRows);
			url = "${pageContext.request.contextPath}/sys/aclModule/update.data?id="
					+ selectedRows.id;
		}

		function openAclUpdateDialog() {
			var selectedRows = $("#acl_dg").datagrid('getSelections');
			if (selectedRows.length != 1) {
				$.messager.alert("系统提示", "请选择一条要编辑的数据！");
				return;
			}
			var row = selectedRows[0];
			$("#sysAcl_dlg").dialog("open").dialog("setTitle", "编辑权限点信息");
			$('#acl_fm').form('load', row);
			url = "${pageContext.request.contextPath}/sys/acl/update.data?id="
					+ row.id;
		}

		function resetValue() {
			$("#aclModule_cc").combotree('setValue', "");
			$("#aclModule_name").val("");
			$("#aclModule_seq").val("");
			$("#aclModule_remark").val("");
		}

		function resetAclValue() {
			$("#acl_cc").combotree('setValue', "");
			$("#acl_name").val("");
			$("#acl_seq").val("");
			$("#acl_url").val("");
			$("#acl_co").combobox('setValues', "1");
		}

		function saveAclModule() {
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
						$("#aclModule_dlg").dialog("close");
						$("#aclModule_tt").tree("reload");
						$('#aclModule_cc').combotree("reload");
						$('#acl_cc').combotree("reload");

					} else {
						$.messager.alert("系统提示", "保存失败:" + result.msg);
						return;
					}
				}
			});
		}

		function saveAcl() {
			$("#acl_fm").form("submit", {
				url : url,
				onSubmit : function() {
					return $(this).form("validate");
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.ret) {
						$.messager.alert("系统提示", "保存成功");
						resetValue();
						$("#sysAcl_dlg").dialog("close");
						$("#acl_dg").datagrid("reload");
					} else {
						$.messager.alert("系统提示", "保存失败:" + result.msg);
						return;
					}
				}
			});
		}

		function closeAclModuleDialog() {
			$("#aclModule_dlg").dialog('close');
			resetValue();
		}
		function closeAclDialog() {
			$("#sysAcl_dlg").dialog('close');
			resetAclValue();
		}

		function searchAcl() {
			$('#acl_dg').datagrid('reload', {
				name : $("#acl_name").val(),
				moduleLevel : $("#acl_level").val(),
				aclModuleId : $("#acl_moduleId").val()

			});
		}
		$('#aclModule_cc').combotree({
			url : '${pageContext.request.contextPath}/sys/aclModule/tree.data',
			loadFilter : function(data) {
				if (data.ret) {
					return data.data;
				}
			}
		});

		$('#acl_cc').combotree({
			url : '${pageContext.request.contextPath}/sys/aclModule/tree.data',
			loadFilter : function(data) {
				if (data.ret) {
					return data.data;
				}
			}
		});

		$('#aclModule_tt').tree({
			url : "${pageContext.request.contextPath}/sys/aclModule/tree.data",
			//checkbox:true,
			loadFilter : function(data) {
				if (data.ret) {
					return data.data;
				}
			},
			onClick : function(node) {
				$("#acl_level").val(node.level);
				$("#acl_moduleId").val(node.id);
				searchAcl();
			}
		});

		$('#acl_dg').datagrid({
			fitColumns : true,
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			fit : true,
			url : "${pageContext.request.contextPath}/sys/acl/selectAll.data",
			columns : [ [ {
				field : 'id',
				width : 50,
				align : 'center',
				title : '编号',
				hidden : true
			}, {
				field : 'name',
				width : 100,
				align : 'center',
				title : '权限名称'
			}, {
				field : 'moduleName',
				width : 60,
				align : 'center',
				title : '权限模块'
			}, {
				field : 'type',
				width : 100,
				align : 'center',
				title : '类型',
				formatter:function(value, row, index){
					if (value == 1) {
						return "菜单";
					} else if (value == 2) {
						return "按钮";
					} else {
						return "其他";
					}
				}
			}, {
				field : 'url',
				width : 150,
				align : 'center',
				title : 'URL'
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
						return value;
					}
				}
			}, {
				field : 'seq',
				width : 60,
				align : 'center',
				title : '顺序',
				formatter : function(value, row, index) {
					if (value == 1) {
						return "正常";
					} else if (value == 0) {
						return "冻结";
					} else {
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