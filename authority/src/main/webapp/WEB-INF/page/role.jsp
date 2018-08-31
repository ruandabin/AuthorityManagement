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
	<div region="center" border="true"></div>
	<div region="west" style="width: 200px" title="" split="false"
		border="false">
		<div id="aclMoudle_panel" class="easyui-panel" title="角色列表"
			data-options="fit:true,tools:'#tt'">
			<ul id="role_tt"></ul>
		</div>
		<div id="tt">
			<a href="javascript:void(0)" class="icon-add"
				onclick="javascript:openRoleAddDialog()"></a> <a
				href="javascript:void(0)" class="icon-edit"
				onclick="javascript:openRoleUpdateDialog()"></a>
		</div>
	</div>

	<div id="role_dlg" class="easyui-dialog" modal="true"
		style="width: 500px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="10px" border="0">
				<tr>
					<td>名称：</td>
					<td><input class="easyui-validatebox" type="text"
						id="role_name" name="name" style="width: 200px;" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select id="role_co" class="easyui-combobox" name="status"
						style="width: 200px;">
							<option value="1">正常</option>
							<option value="0">冻结</option>
					</select></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input class="easyui-validatebox" type="text"
						id="role_remark" name="remark" style="width: 200px;" /></td>
				</tr>

			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:saveRole()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a href="javascript:closeRoleDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>


	<script type="text/javascript">
		var url;

		function openRoleAddDialog() {
			resetRoleValue();
			$("#role_dlg").dialog("open").dialog("setTitle", "添加角色");
			url = "${pageContext.request.contextPath}/sys/role/save.data";
		}

		function openRoleUpdateDialog() {
			var selectedRows = $("#role_tt").tree('getSelected');
			if (selectedRows == null) {
				$.messager.alert("系统提示", "请选择一条要编辑的数据！");
				return;
			}
			$("#role_dlg").dialog("open").dialog("setTitle", "编辑角色信息");
			$('#fm').form('load', selectedRows);
			url = "${pageContext.request.contextPath}/sys/role/update.data?id="
					+ selectedRows.id;
		}

		function resetRoleValue() {
			$("#role_name").val("");
			$("#role_remark").val("");
		}

		function saveRole() {
			$("#fm").form("submit", {
				url : url,
				onSubmit : function() {
					return $(this).form("validate");
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.ret) {
						$.messager.alert("系统提示", "保存成功");
						resetRoleValue();
						$("#role_dlg").dialog("close");
						$("#role_tt").tree("reload");

					} else {
						$.messager.alert("系统提示", "保存失败:" + result.msg);
						return;
					}
				}
			});
		}

		
		function closeRoleDialog() {
			$("#role_dlg").dialog('close');
			resetRoleValue();
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

		$('#role_tt').tree({
			url : "${pageContext.request.contextPath}/sys/role/list.data",
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
	</script>
</body>
</html>