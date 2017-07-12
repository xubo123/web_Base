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
		if ($('#userId').val() > 0) {
			$.ajax({
				url : '${pageContext.request.contextPath}/user/UserAction!getUserByUserId.action',
				data : $('form').serialize(),
				dataType : 'json',
				success : function(result) {
					if (result.userId != undefined) {
						$('form').form('load', {
							'user.userName' : result.userName,
							'user.userAccount' : result.userAccount,
							'user.password':result.password,
							'user.telNum':result.telNum,
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
	if($('#userAccount').val()==''){
			$pjq.messager.alert('提示', '请设置用户账号', 'info');
			return false;
		}
		if($('#password').val()==''){
			$pjq.messager.alert('提示', '请设置用户密码', 'info');
			return false;
		}
		if($('#userName').val()==''){
			$pjq.messager.alert('提示', '请设置用户名称', 'info');
			return false;
		}
		if($('#roleId').val()==''){
			$pjq.messager.alert('提示', '请设置用户角色', 'info');
			return false;
		}
		if($('#telNum').val()==''){
			$pjq.messager.alert('提示', '请填写电话号码', 'info');
			return false;
		}
		if($('#telNum').val().length!=11){
			$pjq.messager.alert('提示', '电话号码不规范', 'info');
			return false;
		}
		if ($('form').form('validate')) {
			var url;
			if ($('#userId').val() > 0) {
				url = '${pageContext.request.contextPath}/user/UserAction!update.action';
			} else {
				url = '${pageContext.request.contextPath}/UserAction!save.action';
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
     <form method="post" id="userForm">
     	<fieldset>
				<legend>
					用户基本信息
				</legend>
				<table class="ta001">
					<input name="ids" id="ids" type="hidden"/>
					<input name="user.roleId" id="roleId" type="hidden"/>
					<input name="user.userId" type="hidden" id="userId" value="${param.id}">
					<c:if test="${param.id==0}">
						<tr>
							<th>
								用户帐号
							</th>
							<td>
								<input id="userAccount" name="user.userAccount" class="easyui-validatebox"
									data-options="required:true" />
							</td>
						</tr>
						<tr>
							<th>
								用户密码
							</th>
							<td>
								<input id="password" name="user.password" class="easyui-validatebox" data-options="required:true,validType:'passWord[6]'" type="password">
							</td>
						</tr>
					</c:if>
					<c:if test="${param.id!=0}">
					<tr>
							<th>
								用户帐号
							</th>
							<td>
								<input id="userAccount" name="user.userAccount" class="easyui-validatebox" disabled="disabled"
									data-options="required:true" />
							</td>
						</tr>
						<tr>
							<th>
								用户密码
							</th>
							<td>
								<input id="password" name="user.password" class="easyui-validatebox" data-options="required:true,validType:'passWord[6]'" type="password">
							</td>
						</tr>
					</c:if>
					<tr>
						<th>
							用户姓名
						</th>
						<td>
							<input id="userName"name="user.userName" class="easyui-validatebox"
								data-options="required:true,validType:'customRequired'" />
						</td>
					</tr>
					<tr id="roletr">
						<th>
							角色
						</th>
						<td >
							<input id="role" class="easyui-combobox" style="width: 150px;" name="role"
											data-options="  
												valueField: 'roleId',  
												textField: 'roleName',  
												editable:false,
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#role').combobox('clear');
									                $('#roleId').prop('value','');
									                }
									            }],
												url: '${pageContext.request.contextPath}/RoleAction!getAllRole.action',  
												onSelect: function(rec){
												$('#roleId').prop('value',rec.roleId);
										}" />
						</td>
					</tr>
					<tr>
						<th>
							电话
						</th>
						<td>
							<input id="telNum" name="user.telNum" class="easyui-validatebox"
								data-options="required:true,validType:'customRequired'" />
						</td>
					</tr>
				</table>
			</fieldset>
     </form>
  </body>
</html>
