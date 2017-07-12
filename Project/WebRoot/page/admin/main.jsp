<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>湖北省对外友好服务中心后台</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<jsp:include page="/inc.jsp"></jsp:include>

</head>

<script type="text/javascript">
	var mainMenu;
	var mainTabs;
	$(function() {
		mainMenu = $('#mainMenu')
				.tree(
						{
							url : '${pageContext.request.contextPath}/LoginAction!doNotNeedSecurity_initMenu.action',
							parentField : 'pid',
							onClick : function(node) {
								if (node.attributes.url) {
									var url = node.attributes.url;
									if (url != '') {
										var tabs = $('#mainTabs');
										var opts = {
											title : node.text,
											closable : true,
											iconCls : node.iconCls,
											content : '<iframe src="${pageContext.request.contextPath}'
													+ url
													+ '" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>',
											border : false,
											fit : true
										};
										if (tabs.tabs('exists', opts.title)) {
											tabs.tabs('select', opts.title);
										} else {
											tabs.tabs('add', opts);
										}
									}
								}
							},
							onBeforeLoad : function(node, param) {
								$.messager.progress({
									text : '数据加载中....'
								});
							},
							onLoadSuccess : function(node, data) {
								$.messager.progress('close')
							}
						});

		$('#mainLayout').layout('panel', 'center').panel(
				{
					onResize : function(width, height) {
						setIframeHeight('centerIframe',
								$('#mainLayout').layout('panel', 'center')
										.panel('options').height - 5);
					}
				});

		mainTabs = $('#mainTabs')
				.tabs(
						{
							fit : true,
							border : false,
							tools : [
									{
										iconCls : 'ext-icon-arrow_up',
										handler : function() {
											mainTabs.tabs({
												tabPosition : 'top'
											});
										}
									},
									{
										iconCls : 'ext-icon-arrow_left',
										handler : function() {
											mainTabs.tabs({
												tabPosition : 'left'
											});
										}
									},
									{
										iconCls : 'ext-icon-arrow_down',
										handler : function() {
											mainTabs.tabs({
												tabPosition : 'bottom'
											});
										}
									},
									{
										iconCls : 'ext-icon-arrow_right',
										handler : function() {
											mainTabs.tabs({
												tabPosition : 'right'
											});
										}
									},
									{
										text : '刷新',
										iconCls : 'ext-icon-arrow_refresh',
										handler : function() {
											var panel = mainTabs.tabs(
													'getSelected').panel(
													'panel');
											var frame = panel.find('iframe');
											try {
												if (frame.length > 0) {
													for (var i = 0; i < frame.length; i++) {
														frame[i].contentWindow.document
																.write('');
														frame[i].contentWindow
																.close();
														frame[i].src = frame[i].src;
													}
													if (navigator.userAgent
															.indexOf("MSIE") > 0) {// IE特有回收内存方法
														try {
															CollectGarbage();
														} catch (e) {
														}
													}
												}
											} catch (e) {
											}
										}
									},
									{
										text : '关闭',
										iconCls : 'ext-icon-cross',
										handler : function() {
											var index = mainTabs
													.tabs(
															'getTabIndex',
															mainTabs
																	.tabs('getSelected'));
											var tab = mainTabs.tabs('getTab',
													index);
											if (tab.panel('options').closable) {
												mainTabs.tabs('close', index);
											} else {
												$.messager
														.alert(
																'提示',
																'['
																		+ tab
																				.panel('options').title
																		+ ']不可以被关闭！',
																'error');
											}
										}
									} ]
						});

		$('#passwordDialog')
				.show()
				.dialog(
						{
							modal : true,
							closable : true,
							iconCls : 'ext-icon-password',
							buttons : [ {
								text : '修改',
								iconCls : 'ext-icon-note_edit',
								handler : function() {
									if ($('#passwordDialog form').form(
											'validate')) {
										$
												.post(
														'${pageContext.request.contextPath}/login/LoginAction!doNotNeedSecurity_updateCurrentPwd.action',
														{
															'pwd' : $('#pwd')
																	.val()
														},
														function(result) {
															if (result.success) {
																$.messager
																		.alert(
																				'提示',
																				result.msg,
																				'info');
																$(
																		'#passwordDialog')
																		.dialog(
																				'close');
															} else {
																$.messager
																		.alert(
																				'提示',
																				result.msg,
																				'error');
															}
														}, 'json');
									}
								}
							} ],
							onOpen : function() {
								$('#passwordDialog form :input').val('');
							}
						}).dialog('close');
	});
</script>
<body id="mainLayout" class="easyui-layout" style="overflow-y: hidden"
	scroll="no">
	<div
		data-options="region:'north',href:'${pageContext.request.contextPath}/north.jsp'"
		style="height: 70px; overflow: hidden;background-image: url('images/cctop.jpg');background-repeat: repeat-x;">
	</div>
	<div data-options="region:'south'" style="height: 30px;">
		<div align="center" style="margin-top: 5px;">Copyright © 2017湖北省对外友好服务中心
			All Rights Reserved.</div>
	</div>
	<div data-options="region:'west',title:'导航菜单',split:true"
		style="width: 180px;">
		<ul id="mainMenu"></ul>
	</div>
	<div data-options="region:'center'">
		<div id="mainTabs">
			<div title="我的主页" data-options="iconCls:'ext-icon-homePage'">
				<iframe src="${pageContext.request.contextPath}/welcome.jsp"
					allowTransparency="true"
					style="border: 0; width: 100%; height: 99%;background-image: url('images/tta1.jpg');background-position: center;background-repeat: no-repeat;"
					frameBorder="0"></iframe>
			</div>
		</div>
	</div>
	<div id="passwordDialog" title="修改密码" style="display: none;">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table">
				<tr>
					<th style="width:80px;">新密码</th>
					<td><input id="pwd" name="data.pwd" type="password"
						class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>重复密码</th>
					<td><input type="password" class="easyui-validatebox"
						data-options="required:true,validType:['eqPassword[\'#pwd\']','passWord[6]','customRequired']" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
