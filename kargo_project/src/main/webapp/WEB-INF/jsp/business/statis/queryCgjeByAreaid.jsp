<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html> 
<head>
<title>按区域统计采购金额</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">




	function cgjequery() {
		/* var formdata = $("#businesslistForm").serializeJson();
		//alert(formdata);
		$('#businesslist').datagrid('unselectAll');
		$('#businesslist').datagrid('load', formdata); */
		
		document.businesslistForm.submit();
	}
	$(function(){
		//加载年
		businessyearlist('businessyear');
	});
</script>
</HEAD>
<BODY>
    <form id="businesslistForm" name="businesslistForm" method="post" action="${baseurl }/statis/queryCgjeByAreaid.action">
			<input type="hidden" name="indexs" id="indexs" />
			<TABLE  class="table_search">
				<TBODY>
					<TR>
						<TD class="left">年份(如2014)：</TD>
						<td >
						<select id="businessyear" name="year"></select>
						</td>
						<TD class="left">医院名称：</TD>
						<td ><INPUT type="text" name="useryyCustom.mc" /></td>
						<TD class="left">供货商：</TD>
						<td ><INPUT type="text" name="usergysCustom.mc" /></td>
						<TD class="left">采购单号：</td>
						<td><INPUT type="text"  name="yycgdCustom.bm" /></TD>
						
						
					</TR>
					<TR>
					   <TD class="left">流水号：</TD>
						<td ><INPUT type="text" name="ypxxCustom.bm" /></td>
					   <TD class="left">通用名：</td>
					    <td><INPUT type="text"  name="ypxxCustom.mc" />
						 </TD>
						<TD class="left">采购时间：</TD>
						<td >
						 <INPUT id="yycgdCustom.kscgdate"
							name="yycgdCustom.kscgdate" 
							 onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:80px"/>--
					 <INPUT id="yycgdCustom.jscgdate" 
							name="yycgdCustom.jscgdate"
							 onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:80px"/>
							
						</td>
						<TD class="left">采购状态：</TD>
						<td >
							<select id="yycgdCustom.cgzt" name="yycgdCustom.cgzt" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${cgztList}" var="value">
									<option value="${value.dictcode}">${value.info}</option>
								</c:forEach>
							</select>
							<a id="btn" href="#" onclick="cgjequery()" class="easyui-linkbutton" iconCls='icon-search'>统计</a>
							
						</td>
						
						
					</tr>
					
					
				</TBODY>
			</TABLE>
	   
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
	<img src="${baseurl }/jfreechart?filename=${jfreechart_filename }"  border=0/>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		 </form>

</BODY>
</HTML>

