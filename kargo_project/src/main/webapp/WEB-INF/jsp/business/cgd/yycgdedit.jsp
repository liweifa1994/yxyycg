<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html> 
<head>
<title>采购单修改</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">

//采购单保存
function yycgdsave(){
	$("#yycgdsaveForm").attr("action","${baseurl}/cgd/yycgdsave.action");
	jquerySubByFId('yycgdsaveForm', yycgdsave_callback, null);
}
//采购单提交
function yycgdsubmit(){
	$("#yycgdsaveForm").attr("action","${baseurl}/cgd/yycgdsubmit.action");
	jquerySubByFId('yycgdsaveForm', yycgdsave_callback, null);
}
function yycgdsave_callback(data) {
	var result = getCallbackData(data);
	_alert(result);
	//如果成功刷新页面，重新跳转至修改页面
	if(result.type==TYPE_RESULT_SUCCESS){
		//alert(result.sysdata.yycgd.id);
		var yycgdid=result.sysdata.yycgd.id;
		window.location='${baseurl}/cgd/yycgdedit.action?yycgdid='+yycgdid;
	}
	
}

var yycgdmxdelete = function(){
	_confirm('您确定要删除选择的采购药品吗?',null,
	  function(){
				
		var indexs = [];//选择记录的序号
		var rows = $('#yycgdmxlist').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			var index=$('#yycgdmxlist').datagrid('getRowIndex',rows[i]);
			indexs.push(index);
		}
		if(rows.length>0){
			$("#indexs").val(indexs.join(','));
			$("#yycgdmxForm").attr("action",'${baseurl}/cgd/yycgdmxdelete.action?yycgdid=${yycgd.id}');
			jquerySubByFId('yycgdmxForm', yycgdmxdelete_callback, null);
		}else{
			alert_warn("请选择要删除的采购药品");
		}
	  }
	)
	
};
//保存采购药品信息
function yycgdmxsave(){
	_confirm("您确定要保存选择的采购药品信息吗？",null,function(){
		
		var indexs = [];//选择记录的序号
		var rows = $('#yycgdmxlist').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			var index=$('#yycgdmxlist').datagrid('getRowIndex',rows[i]);
			indexs.push(index);
		}
		if(rows.length>0){
			$("#indexs").val(indexs.join(','));
			$("#yycgdmxForm").attr("action",'${baseurl}/cgd/yycgdmxsave.action');
			jquerySubByFId('yycgdmxForm', yycgdmxsave_callback, null);
		}else{
			alert_warn("请选择要保存的采购药品");
		}
		
	});
}
function yycgdmxdelete_callback(data) {
	var result = getCallbackData(data);
	_alert(result);
	yycgdmxquery();
}
function yycgdmxsave_callback(data) {
	var result = getCallbackData(data);
	_alert(result);
}
//采购药品添加
function yycgdmxaddshow(){
	var sendUrl="${baseurl}/cgd/yycgdmxadd.action?yycgdid=${yycgd.id}";
	createmodalwindow("供货药品添加", 1000, 500, sendUrl);
}


//工具栏
var toolbar = [{
	id : 'yycgdmxaddshow',
	text : '采购药品添加',
	iconCls : 'icon-add',
	handler : yycgdmxaddshow
	}
	,{
		id : 'yycgdmxdelete',
		text : '采购药品删除',
		iconCls : 'icon-add',
		handler : yycgdmxdelete
	}
	,{
		id : 'yycgdmxsave',
		text : '保存采购药品信息',
		iconCls : 'icon-add',
		handler : yycgdmxsave
	}
	];

var frozenColumns;

var columns = [ [{
	checkbox:true
},{
	field : 'useryymc',
	title : '医院',
	width : 100,
	formatter: function(value,row,index){
		if(value){
			return value;
		}else{
			return '总计';
		}
		
	}
},{
	field : 'ypxxid',
	hidden : true,
	formatter: function(value,row,index){
		if(row.ypxxid){
			return '<input type="hidden" name="yycgdmxs['+index+'].ypxxid" value="'+row.ypxxid+'" />';
		}else{
			return value;
		}
		
	}
},{
	field : 'yycgdid',
	hidden : true,
	formatter: function(value,row,index){
		if(row.ypxxid){
			return '<input type="hidden" name="yycgdmxs['+index+'].yycgdid" value="'+row.yycgdid+'" />';
		}else{
			return value;
		}
	}
},{
	field : 'usergysid',
	hidden : true,
	formatter: function(value,row,index){
		if(row.ypxxid){
			return '<input type="hidden" name="yycgdmxs['+index+'].usergysid" value="'+row.usergysid+'" />';
		}else{
			return value;
		}
	}
},{
	field : 'ypxxbm',
	title : '流水号',
	width : 50
},{
	field : 'ypxxmc',
	title : '通用名',
	width : 100
},{
	field : 'jx',
	title : '剂型',
	width : 70
},{
	field : 'gg',
	title : '规格',
	width : 70
},{
	field : 'zhxs',
	title : '转换系数',
	width : 50
},{
	field : 'zbjg',
	title : '中标价',
	width : 50
},{
	field : 'jyjg',
	title : '交易价',
	width : 50,
	formatter:function(value,row,index){
		if(row.ypxxid){
		   return '<input type="text" name="yycgdmxs['+index+'].jyjg" value="'+(value?value:row.zbjg)+'" />';
		}else{
			return value;
		}
	}
},{
	field : 'cgl',
	title : '采购量',
	width : 50,
	formatter:function(value,row,index){
		if(row.ypxxid){
		   return '<input type="text" name="yycgdmxs['+index+'].cgl" value="'+(value?value:"")+'" />';
		}else{
			return value;
		}
	}
},{
	field : 'cgje',
	title : '采购金额',
	width : 50
},{
	field : 'scqymc',
	title : '生产企业',
	width : 100
},{
	field : 'spmc',
	title : '商品名称',
	width : 100
},{
	field : 'cgztmc',
	title : '采购状态',
	width : 80
},{
	field : 'usergysmc',
	title : '供货商',
	width : 100
}]];

function initGrid(){
	$('#yycgdmxlist').datagrid({
		title : '采购药品列表',
		//nowrap : false,
		showFooter:true,
		striped : true,
		//collapsible : true,
		url : '${baseurl}/cgd/yycgdedit_cgdmxresult.action?yycgdid=${yycgd.id}',
		//sortName : 'code',
		//sortOrder : 'desc',
		//remoteSort : false,
		idField : 'yycgdmxid',//采购药品明细id
		//frozenColumns : frozenColumns,
		columns : columns,
		pagination : true,
		rownumbers : true,
		toolbar : toolbar,
		loadMsg:"",
		pageList:[15,30,50,100]
		
		} );

	}
	$(function() {
		initGrid();
	});

	function yycgdmxquery() {
		var formdata = $("#yycgdmxForm").serializeJson();
		//alert(formdata);
		$('#yycgdmxlist').datagrid('unselectAll');
		$('#yycgdmxlist').datagrid('load', formdata);
	}
</script>
</HEAD>
<BODY>

<form id="yycgdsaveForm" name="yycgdsaveForm" action="${baseurl}/cgd/yycgdsave.action" method="post">
<input type="hidden" name="yycgd.id" value="${yycgd.id}"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="70%" bgColor=#c4d8ed align=center>
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;药品采购单</TD>
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
								<TD height=30 width="15%" align=right>采购单编号：</TD>
								<TD class=category width="35%">
								${yycgd.bm}
								</TD>
								<TD height=30 width="15%" align=right >采购单名称：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="yycgd_mc" name="yycgd.mc" value="${yycgd.mc}"  style="width:260px" />
								</div>
								<div id="yycgd_mcTip"></div>
								</TD>
							</TR>
							<TR>
							   <TD height=30 width="15%" align=right >建单时间：</TD>
								<TD class=category width="35%">
									<fmt:formatDate value="${yycgd.cjtime}" pattern="yyyy-MM-dd"/>
								</TD>
								<TD height=30 width="15%" align=right >提交时间：</TD>
								<TD class=category width="35%">
								<fmt:formatDate value="${yycgd.tjtime}" pattern="yyyy-MM-dd"/>
								</TD>
								
							</TR>
							<TR>
								<TD height=30 width="15%" align=right>联系人：</TD>
								<TD class=category width="35%">
								<input type="text" name="yycgd.lxr" id="yycgd.lxr" value="${yycgd.lxr}"  style="width:260px" />
								</TD>
								<TD height=30 width="15%" align=right >联系电话：</TD>
								<TD class=category width="35%">
								<input type="text" name="yycgd.lxdh" id="yycgd.lxdh" value="${yycgd.lxdh}"  style="width:260px" />
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right>采购单状态：</TD>
								<TD class=category width="35%">
								${yycgdCustom.yycgdztmc}
								</TD>
								<TD height=30 width="15%" align=right>备注：</TD>
								<TD colspan=3>
									<textarea rows="2" cols="30" name="yycgd.bz">${yycgd.bz}</textarea>
								</TD>
							</TR>
							
							<TR>
								<TD height=30 width="15%" align=right>审核时间：</TD>
								<TD class=category width="35%">
								<fmt:formatDate value="${yycgd.shtime}" pattern="yyyy-MM-dd"/>
								</TD>
								<TD height=30 width="15%" align=right >审核意见：</TD>
								<TD class=category width="35%">
								${yycgd.shyj}
								</TD>
							</TR>
							
							<tr>
							  <td colspan=4 align=center class=category>
								<a  href="#" onclick="yycgdsave()" class="easyui-linkbutton" iconCls='icon-save'>保存</a>
								 <a  href="#" onclick="yycgdsubmit()" class="easyui-linkbutton" iconCls='icon-ok'>提交</a>
							  </td>
							</tr>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>

<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;采购药品列表</TD>
								<TD align=right>&nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
 <form id="yycgdmxForm" name="yycgdmxForm" method="post">
	<input type="hidden" name="indexs" id="indexs" />
	
			<TABLE  class="table_search">
				<TBODY>
					<TR>
						
						<TD class="left">通用名：</td>
						<td><INPUT type="text"  name="ypxxCustom.mc" /></TD>
						<TD class="left">剂型：</TD>
						<td ><INPUT type="text" name="ypxxCustom.jx" /></td>
						<TD class="left">规格：</TD>
						<td ><INPUT type="text" name="ypxxCustom.gg" /></td>
						<TD class="left">转换系数：</TD>
						<td ><INPUT type="text" name="ypxxCustom.zhxs" /></td>
					</TR>
					<TR>
						<TD class="left">流水号：</TD>
						<td ><INPUT type="text" name="ypxxCustom.bm" /></td>
						<TD class="left">生产企业：</TD>
						<td ><INPUT type="text" name="ypxxCustom.scqymc" /></td>
						<TD class="left">商品名称：</TD>
						<td ><INPUT type="text" name="ypxxCustom.spmc" /></td>
						 <td class="left">价格范围：</td>
				  		<td>
				      		<INPUT id="ypxxCustom.zbjglower" name="ypxxCustom.zbjglower" style="width:70px"/>
							至
							<INPUT id="ypxxCustom.zbjgupper" name="ypxxCustom.zbjgupper" style="width:70px"/>
							
				 		 </td>
					</tr>
					<tr>
					  
						<TD class="left">药品类别：</TD>
						<td >
							<select id="ypxxCustom.lb" name="ypxxCustom.lb" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${yplbList}" var="value">
									<option value="${value.id}">${value.info}</option>
								</c:forEach>
							</select>
						</td>
						<TD class="left">交易状态：</TD>
						<td >
							<select id="ypxxCustom.jyzt" name="ypxxCustom.jyzt" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${ypjyztList}" var="value">
									<option value="${value.dictcode}">${value.info}</option>
								</c:forEach>
							</select>
							
						</td>
						
				 		 <td class="left" height="25">供货状态：</td>
				  		<td>
				  		<select id="control" name="control" style="width:80px">
								<option value="">全部</option>
								<option value="1">正常</option>
								<option value="2">无法供货</option>
						</select>
					
				  		</td>

				  		<td colspan=2>
				  		
						<a id="btn" href="#" onclick="yycgdmxquery()" class="easyui-linkbutton" iconCls='icon-search'>查询</a>
				  		</td>
						
					</TR>
					
				</TBODY>
			</TABLE>
	   
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="yycgdmxlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>

</BODY>
</HTML>

