<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
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
	<jsp:include page="../../../inc.jsp"></jsp:include>
	<script type="text/javascript">
	var hasCheck=0;
	$(function() {
		if ($('#roleId').val() > 0) {
			$.ajax({
				url : '${pageContext.request.contextPath}/RoleAction!getRoleByRoleId.action',
				data : $('form').serialize(),
				dataType : 'json',
				success : function(result) {
					if (result.roleId != undefined) {
						$('form').form('load', {
							'role.roleName' : result.roleName,
							
						});

					}
				},
				complete:function(){
					parent.$.messager.progress('close');
				}
			});
		}
	});
	
	var submitForm = function($dialog, $grid, $pjq) {
	if($('#roleName').val()==''){
			$pjq.messager.alert('提示', '请设置角色名称', 'info');
			return false;
		}
		if ($('form').form('validate')) {
			var url;
			if ($('#roleId').val() > 0) {
				url = '${pageContext.request.contextPath}/RoleAction!update.action';
			} else {
				url = '${pageContext.request.contextPath}/RoleAction!save.action';
			}
			$.ajax({
				url : url,
				data :$('form').serialize(),
				dataType : 'json',
				success : function(result) {
					if (result.success) {
						$dialog.dialog('destroy');
						$grid.datagrid('reload');
						$pjq.messager.alert('提示', result.msg, 'info');
					} else {
						$pjq.messager.alert('提示', result.msg, 'error');
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
	};
	</script>
  </head>
  
  <body>
     <form method="post" id="roleForm">
     	<fieldset>
				<legend>
					角色信息
				</legend>
				<table class="ta001">
					<input name="role.roleId" type="hidden" id="roleId" value="${param.id}">
						
						<tr>
							<th>
								角色名称
							</th>
							<td>
								<input id="roleName" name="role.roleName" class="easyui-validatebox"
									data-options="required:true" />
							</td>
						</tr>
					
				</table>
			</fieldset>
     </form>
  </body>
</html>
