<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>

<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<%
	String contextPath = request.getContextPath();
%>

<%--
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
--%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<%
	Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
Cookie[] cookies = request.getCookies();
if (null != cookies) {
	for (Cookie cookie : cookies) {
		cookieMap.put(cookie.getName(), cookie);
	}
}
String easyuiTheme = "metro-blue";//指定如果用户未选择样式，那么初始化一个默认样式
if (cookieMap.containsKey("easyuiTheme")) {
	Cookie cookie = (Cookie) cookieMap.get("easyuiTheme");
	easyuiTheme = cookie.getValue();
}
%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jslib/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jslib/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jslib/ExtJquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jslib/ExtEasyUI.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/icons/icon-all.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/frame.css" type="text/css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/icon.css" type="text/css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jslib/easyui/themes/icon.css"
	type="text/css"></link>
<link id="easyuiTheme" rel="stylesheet"
	href="${pageContext.request.contextPath}/jslib/easyui/themes/<%=easyuiTheme%>/easyui.css"
	type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jslib/jquery-dateFormat.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jslib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jslib/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jslib/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jslib/ajaxupload.3.6.js"></script>

<link href="favicon.ico" rel="shortcut icon" />
</script>
<script type="text/javascript">
var pixel_0 = '${pageContext.request.contextPath}/images/pixel_0.gif';//0像素的背景，一般用于占位
var contextPath = '<%=basePath%>';
</script>