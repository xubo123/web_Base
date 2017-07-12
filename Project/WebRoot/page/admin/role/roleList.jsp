<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/inc.jsp"></jsp:include>
	<script type="text/javascript">
		var roleGrid;
		$(function(){
			roleGrid = $('#roleGrid').datagrid({
				url : '${pageContext.request.contextPath}/RoleAction!dataGrid.action',
				fit : true,
				border : false,
				fitColumns : true,
				striped : true,
				rownumbers : true,
				pagination : true,
				singleSelect : true,
				idField : 'userId',
				columns : [ [
					{
						width : '100',
						title : '角色名称',
						field : 'roleName',
						align:'center'
					},
					{
						width : '100',
						title : '是否为管理员',
						field : 'systemAdmin',
						align:'center',
						formatter : function(value, row) {
							if(row.systemAdmin!=1){
								return "非管理员";
							}else{
							    return "管理员";
							}
						}
					},
					{
					    title : '操作',
						field : 'action',
						width : '100',
						formatter : function(value, row) {
							if(row.roleId!=1&&row.roleId!=2){
 							var str = '';
/* 									str += '<a href="javascript:void(0)" onclick="showFun('+ row.roleId + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
 */									str += '<a href="javascript:void(0)" onclick="editFun('+ row.roleId + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
									str += '<a href="javascript:void(0)" onclick="removeFun('+ row.roleId + ');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
									return str;
							}else{
							var str = '';
 									str += '<a href="javascript:void(0)" onclick="editFun('+ row.roleId + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
									return str;
							}
							}
						} ] ],
						toolbar : '#toolbar',
					onBeforeLoad : function(param) {
						parent.$.messager.progress({
							text : '数据加载中....'
						});
					},
					onLoadSuccess : function(data) {
						$('.iconImg').attr('src', pixel_0);
						parent.$.messager.progress('close');
					}
				});
		});
		
		function addFun(){
			var dialog = parent.WidescreenModalDialog({
				title : '新增角色',
				iconCls : 'ext-icon-note_add',
				url :  '${pageContext.request.contextPath}/page/admin/role/roleForm.jsp?id=0',
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, roleGrid, parent.$);
					}
				} ]
			});
		}
/* 		var showFun = function(id) {
			var dialog = parent.WidescreenModalDialog({
				title : '查看用户信息',
				iconCls : 'ext-icon-note',
				url : '${pageContext.request.contextPath}/page/admin/role/viewRole.jsp?id=' + id
			});
		}; */
		var editFun = function(id) {
			var dialog = parent.WidescreenModalDialog({
				title : '编辑用户信息',
				iconCls : 'ext-icon-note_edit',
				url : '${pageContext.request.contextPath}/page/admin/role/roleForm.jsp?id=' + id,
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, roleGrid, parent.$);
					}
				} ]
			});
		};
		var removeFun = function(id) {
			parent.$.messager.confirm('确认', '您确定要删除此记录？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/RoleAction!delete.action',
						data : {
						     roleId : id
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								roleGrid.datagrid('reload');
								parent.$.messager.alert('提示', result.msg, 'info');
							} else {
								parent.$.messager.alert('提示', result.msg, 'error');
							}
						},
						beforeSend:function(){
							parent.$.messager.progress({
								text : '数据提交中....'
							});
						},
						complete:function(){
							parent.$.messager.progress('close');
						}
					});
				}
			});
		};
	</script>
  </head>
  
  <body class="easyui-layout" data-options="fit:true,border:false">
		<div id="toolbar" style="display: none;">
			<table>
				<!-- <tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									<th>
										角色名称
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="roleName" style="width: 150px;" />
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'icon-search',plain:true"
											onclick="userGrid.datagrid('load',{userAccount:$('#userAccount').val()});">查询</a>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr> -->
				<tr>
					<td>
						<table>
							<tr>
								<td>
									 <authority:authority role="${sessionScope.user.role}" authorizationCode="新增角色">
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'ext-icon-note_add',plain:true"
											onclick="addFun();">新增</a>
									</authority:authority> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="roleGrid"></table>
		</div>
	</body>
</html>
