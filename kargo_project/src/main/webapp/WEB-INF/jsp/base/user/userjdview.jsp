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
		
	$(function (){
		//***********按钮**************
		$('#closebtn').linkbutton({   
    		iconCls: 'icon-cancel'  
		});
		
		
	});
	
	</script>
 </HEAD>
<BODY>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;监督单位信息</TD>
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
								<TD height=30 width="15%" align=right >监督单位名称：</TD>
								<TD class=category width="35%">
								<div>
								${userjd.mc}
								</div>
								</TD>
								<TD height=30 width="15%" align=right>管理地区：</TD>
								<TD class=category width="35%">
								<div>
								${userjddqmc}
								</div>
								</TD>
							</TR>
							
							
							<TR>
								<TD height=30 width="15%" align=right >通讯地址：</TD>
								<TD class=category width="35%">
								${userjd.dz}
								</TD>
								<TD height=30 width="15%" align=right>邮政编码：</TD>
								<TD class=category width="35%">
								${userjd.yzbm}
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >电话：</TD>
								<TD class=category width="35%">
								${userjd.dh}
								</TD>
								<TD height=30 width="15%" align=right>传真：</TD>
								<TD class=category width="35%">
								${userjd.cz}
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >email：</TD>
								<TD class=category width="35%">
								${userjd.dzyx}
								</TD>
								<TD height=30 width="15%" align=right>网址：</TD>
								<TD class=category width="35%">
								${userjd.wz}
								</TD>
							</TR>
							<tr>
							  <td colspan=4 align=center class=category>
								<a id="closebtn" href="#" onclick="parent.closemodalwindow()">关闭</a>
							  </td>
							</tr>
						
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>


</BODY>
</HTML>

