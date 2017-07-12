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
	$(function() {
		if ($('#userId').val() > 0) {
			$.ajax({
				url : '${pageContext.request.contextPath}/UserAction!getUserByUserId.action',
				data : $('form').serialize(),
				dataType : 'json',
				success : function(result) {
					if (result.userId != undefined) {
						$('form').form('load', {
							'user.userName' : result.userName,
							'user.userAccount' : result.userAccount,
							'user.password':result.password,
							'user.roleId':result.roleId,
							'user.role.roleName':result.role.roleName,
							'user.userId':result.userId,
							'user.telNum':result.telNum,
						});
						
					}
				},
				complete:function(){
					parent.$.messager.progress('close');
				}
			});
			$("#role").combobox("disable"); 
		}
	});
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
								<input id="userAccount" name="user.userAccount" class="easyui-validatebox" disabled="disabled"
									data-options="required:true" />
							</td>
						</tr>
						<tr>
							<th>
								用户密码
							</th>
							<td>
								<input id="password" name="user.password" class="easyui-validatebox" disabled="disabled" data-options="required:true,validType:'passWord[6]'" type="password">
							</td>
						</tr>
					</c:if>
					
					<tr>
						<th>
							用户姓名
						</th>
						<td>
							<input id="userName"name="user.userName" class="easyui-validatebox" disabled="disabled"
								data-options="required:true,validType:'customRequired'" />
						</td>
					</tr>
					<tr>
						<th>
							角色
						</th>
						<td>
							<input name="user.role.roleName" class="easyui-validatebox" disabled="disabled"
								data-options="required:true,validType:'customRequired'" />
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
