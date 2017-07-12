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
		var userGrid;
		$(function(){
			userGrid = $('#userGrid').datagrid({
				url : '${pageContext.request.contextPath}/UserAction!dataGrid.action',
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
						title : '用户姓名',
						field : 'userName',
						align:'center'
					},
					{
						width : '100',
						title : '用户帐号',
						field : 'userAccount',
						align:'center'
					},
					{
						width : '100',
						title : '电话',
						field : 'telNum',
						align:'center'
					},
					{
						width : '100',
						title : '角色',
						field : 'roleName',
						align:'center',
						formatter : function(value, row) {
							if(row.role!=undefined){
								return row.role.roleName;
							}
						}
					},
					{
					    title : '操作',
						field : 'action',
						width : '80',
						formatter : function(value, row) {
/* 							if(row.role!=undefined&&row.role.systemAdmin==1){
 */								var str = '';
									str += '<a href="javascript:void(0)" onclick="showFun('+ row.userId + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
									str += '<a href="javascript:void(0)" onclick="editFun('+ row.userId + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
									str += '<a href="javascript:void(0)" onclick="removeFun('+ row.userId + ');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
									return str;
							/* } */
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
				title : '新增用户信息',
				iconCls : 'ext-icon-note_add',
				url :  '${pageContext.request.contextPath}/page/admin/user/userForm.jsp?id=0',
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, userGrid, parent.$);
					}
				} ]
			});
		}
		var showFun = function(id) {
			var dialog = parent.WidescreenModalDialog({
				title : '查看用户信息',
				iconCls : 'ext-icon-note',
				url : '${pageContext.request.contextPath}/page/admin/user/viewUser.jsp?id=' + id
			});
		};
		var editFun = function(id) {
			var dialog = parent.WidescreenModalDialog({
				title : '编辑用户信息',
				iconCls : 'ext-icon-note_edit',
				url : '${pageContext.request.contextPath}/page/admin/user/userForm.jsp?id=' + id,
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, userGrid, parent.$);
					}
				} ]
			});
		};
		var removeFun = function(id) {
			parent.$.messager.confirm('确认', '您确定要删除此记录？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/UserAction!delete.action',
						data : {
						     userId : id
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								userGrid.datagrid('reload');
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
				<tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									<th>
										用户帐号
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="userAccount" style="width: 150px;" />
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
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td>
									 <authority:authority role="${sessionScope.user.role}" authorizationCode="新增用户">
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
			<table id="userGrid"></table>
		</div>
	</body>
</html>
