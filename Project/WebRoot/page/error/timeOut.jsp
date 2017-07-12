<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<style type="text/css">
			#box {
				height: 270px;
				width: 500px;
				margin-top: -135px;
				margin-left: -250px;
				position: absolute;
				left: 50%;
				top: 50%;
				background-image: url(images/timeout.jpg);
				background-repeat: no-repeat;
				background-position: center center;
			}
			
			.box002 {
				display: block;
				height: 40px;
				width: auto;
				margin-right: auto;
				margin-left: auto;
				clear: both;
				text-align: center;
				vertical-align: middle;
				line-height: 40px;
				margin-top: 153px;
			}
			
			a.box11 {
				font-size: 24px;
				font-weight: bolder;
				color: #00F;
				text-decoration: none;
			}
			
			a.box11:hover {
				font-size: 24px;
				font-weight: bolder;
				color: #00F;
				text-decoration: underline;
			}
	</style>
	<script type="text/javascript">
		function reLogin(){
			if(window.parent==window){
				location.href='<%=basePath%>';
			}else{
				parent.location.href='<%=basePath%>';
			}
		}
	</script>
	</head>

	<body>
		<div id="box">
			<div class="box002">
				<a href="javascript:void(0)" onclick="reLogin()" class="box11">重新登录</a>
			</div>
		</div>
	</body>
</html>
