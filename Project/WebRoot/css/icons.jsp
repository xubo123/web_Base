<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var selectIcon = function($dialog, $input) {
		$input.val($(':radio:checked').val()).attr('class', $(':radio:checked').val());
		$dialog.dialog('destroy');
	};
	$(function() {
		$(':radio').each(function(index) {//初始化小图标
			$(this).after('<img class="iconImg ' + $(this).val() + '"/>');
		});

		$('td').click(function() {//绑定点击td事件，作用是点击td的时候，就可以选中，不一定非得点击radio组件
			$(this).find(':radio').attr('checked', 'checked');
		});

	});
</script>
</head>
<body>
	<table class="table" style="width: 100%;">
		<tr>
			<td><input name="r" value="ext-icon-role" type="radio" /></td>
			<td><input name="r" value="ext-icon-password" type="radio" /></td>
			<td><input name="r" value="ext-icon-save" type="radio" /></td>
			<td><input name="r" value="ext-icon-freeze" type="radio" /></td>
			<td><input name="r" value="ext-icon-thaw" type="radio" /></td>
			<td><input name="r" value="ext-icon-micro" type="radio" /></td>
			<td><input name="r" value="ext-icon-share" type="radio" /></td>
			<td><input name="r" value="ext-icon-activity" type="radio" /></td>
		</tr>
		<tr>
			<td><input name="r" value="ext-icon-activitynews" type="radio" /></td>
			<td><input name="r" value="ext-icon-news" type="radio" /></td>
			<td><input name="r" value="ext-icon-groups" type="radio" /></td>
			<td><input name="r" value="ext-icon-backmenu" type="radio" /></td>
			<td><input name="r" value="ext-icon-area" type="radio" /></td>
			<td><input name="r" value="ext-icon-department" type="radio" /></td>
			<td><input name="r" value="ext-icon-order" type="radio" /></td>
			<td><input name="r" value="ext-icon-orderList" type="radio" /></td>
		</tr>
		<tr>
			<td><input name="r" value="ext-icon-newsCenter" type="radio" /></td>
			<td><input name="r" value="ext-icon-homePage" type="radio" /></td>
			<td><input name="r" value="ext-icon-charity" type="radio" /></td>
			<td><input name="r" value="ext-icon-agentList" type="radio" /></td>
			<td><input name="r" value="ext-icon-customerList" type="radio" /></td>
			<td><input name="r" value="ext-icon-arrow_left" type="radio" /></td>
			<td><input name="r" value="ext-icon-arrow_refresh" type="radio" /></td>
			<td><input name="r" value="ext-icon-book" type="radio" /></td>
		</tr>
		<tr>
			<td><input name="r" value="ext-icon-cog" type="radio" /></td>
			<td><input name="r" value="ext-icon-application" type="radio" /></td>
			<td><input name="r" value="ext-icon-rainbow" type="radio" /></td>
			<td><input name="r" value="ext-icon-house" type="radio" /></td>
			<td><input name="r" value="ext-icon-house_go" type="radio" /></td>
			<td><input name="r" value="ext-icon-door_out" type="radio" /></td>
			<td><input name="r" value="ext-icon-disconnect" type="radio" /></td>
			<td><input name="r" value="ext-icon-arrow_down" type="radio" /></td>
		</tr>
		<tr>
			<td><input name="r" value="ext-icon-note_edit" type="radio" /></td>
			<td><input name="r" value="ext-icon-note" type="radio" /></td>
			<td><input name="r" value="ext-icon-note_add" type="radio" /></td>
			<td><input name="r" value="ext-icon-note_delete" type="radio" /></td>
			<td><input name="r" value="ext-icon-monitor" type="radio" /></td>
			<td><input name="r" value="ext-icon-arrow_up" type="radio" /></td>
			<td><input name="r" value="ext-icon-cross" type="radio" /></td>
			<td><input name="r" value="ext-icon-database" type="radio" /></td>
		</tr>
		<tr>
			<td><input name="r" value="ext-icon-arrow_right" type="radio" /></td>
			<td><input name="r" value="ext-icon-moneys" type="radio" /></td>
			<td><input name="r" value="ext-icon-recyle" type="radio" /></td>
			<td><input name="r" value="ext-icon-agent" type="radio" /></td>
			<td><input name="r" value="ext-icon-customer" type="radio" /></td>
			<td><input name="r" value="ext-icon-zoom" type="radio" /></td>
			<td><input name="r" value="ext-icon-user" type="radio" /></td>
			<td><input name="r" value="ext-icon-key" type="radio" /></td>
		</tr>
		<tr>
			<td><input name="r" value="ext-icon-money_book" type="radio" /></td>
			<td><input name="r" value="ext-icon-money_book_list" type="radio" /></td>
			<td><input name="r" value="ext-icon-ordercheck" type="radio" /></td>
			<td><input name="r" value="ext-icon-registercode" type="radio" /></td>
			<td><input name="r" value="ext-icon-agent_role" type="radio" /></td>
			<td><input name="r" value="ext-icon-win_record" type="radio" /></td>
			<td><input name="r" value="ext-icon-export_customer" type="radio" /></td>
			<td><input name="r" value="ext-icon-import_customer" type="radio" /></td>
		</tr>
		<tr>
			<td><input name="r" value="ext-icon-onekey" type="radio" /></td>
			<td><input name="r" value="ext-icon-import_order" type="radio" /></td>
			<td><input name="r" value="ext-icon-prize_setting" type="radio" /></td>
			<td><input name="r" value="ext-icon-confirm_send" type="radio" /></td>
			<td><input name="r" value="ext-icon-yes" type="radio" /></td>
			<td><input name="r" value="ext-icon-no" type="radio" /></td>
			<td><input name="r" value="ext-icon-choose_role" type="radio" /></td>
			<td><input name="r" value="ext-icon-commission" type="radio" /></td>
		</tr>
		<tr>
			<td><input name="r" value="ext-icon-sale" type="radio" /></td>
		</tr>
	</table>
</body>
</html>