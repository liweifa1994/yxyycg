<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">
	$(function() {
		//*************按钮*************
		$('#submitbtn').linkbutton({
			iconCls : 'icon-ok'
		});
		$('#resetbtn').linkbutton({
			iconCls : 'icon-redo'
		});
		//**********表单校验*************
		$.formValidator.initConfig({
			formID : "saveapwdform",
			theme : "Default",
			onError : function(msg, obj, errorlist) {
				//alert(msg);
			}
		});
		//密码
		$("#oldpwd").formValidator({
			onShow : "",
			onCorrect : "&nbsp;"
		}).inputValidator({
			min : 1,
			onError : "请输入密码"
		});
		//新密码
		$("#newpwd").formValidator({
			onShow : "",
			onCorrect : "&nbsp;"
		}).inputValidator({
			min : 1,
			onError : "请输入新密码"
		});
		$("#pwdrepeat").formValidator({
			onShow : "",
			onCorrect : "&nbsp;"
		}).inputValidator({
			min : 1,
			onError : "请重复输入新密码"
		}).compareValidator({
			desID : "newpwd",
			operateor : "=",
			onError : "两次新密码输入不一致"
		});
		
	});
	function saveapwdsubmit() {
		if ($.formValidator.pageIsValid()) {
			jquerySubByFId('saveapwdform', saveapwd_commit_callback, null,
					"json");
		}

	}
	function saveapwd_commit_callback(data) {
		var result = getCallbackData(data);
		_alert(result);
	}
	function reset() {
		document.all('saveapwdform').reset()
	}
</script>
</HEAD>
<BODY>
	<form id="saveapwdform" name="saveapwdform"
		action="${baseurl}/saveapwd.action" method="post">
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%"
			bgColor=#c4d8ed>
			<TBODY>
				<TR>
					<TD background=images/r_0.gif width="100%">
						<TABLE cellSpacing=0 cellPadding=0 width="100%">
							<TBODY>
								<TR>
									<TD>&nbsp;修改密码</TD>
									<TD align=right>&nbsp;</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD>
						<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
							align=center>
							<TBODY>

								<TR>
									<TD height=30 align=right width="15%">旧密码：</TD>
									<TD class=category>
										<div>
											<input type="password" id="oldpwd" name="oldpwd" />
										</div>
										<div id="oldpwdTip"></div>
									</TD>
								</TR>
								<TR>
									<TD height=30 align=right>新密码：</TD>
									<TD class=category>
										<div>
											<input type="password" id="newpwd" name="newpwd" />
										</div>
										<div id="newpwdTip"></div>
									</TD>
								</TR>
								<TR>
									<TD height=30 align=right>重复新密码：</TD>
									<TD class=category>
										<div>
											<input type="password" id="pwdrepeat" name="newpwd" />
										</div>
										<div id="pwdrepeatTip"></div>
									</TD>
								</TR>

								<TR>
									<TD height=30>&nbsp;</TD>
									<TD class=category><a id="submitbtn" href="#"
										onclick="saveapwdsubmit()">提交</a> <a id="resetbtn" href="#"
										onclick="reset()">重置</a></TD>
								</TR>

							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>

</BODY>
</HTML>

