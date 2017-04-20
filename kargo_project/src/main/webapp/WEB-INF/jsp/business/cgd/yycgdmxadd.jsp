<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html> 
<head>
<title>采购药品添加</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">

var yycgdmxaddsubmit = function(){
	_confirm('您确定要采购选择的药品吗?',null,
	  function(){
				
		var indexs = [];//删除记录的序号
		var rows = $('#yyypmllist').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			var index=$('#yyypmllist').datagrid('getRowIndex',rows[i]);
			indexs.push(index);
		}
		if(rows.length>0){
			$("#indexs").val(indexs.join(','));
			jquerySubByFId('yyypmlForm', yycgdmxaddsubmit_callback, null);
		}else{
			alert_warn("请选择要采购的药品");
		}
	  }
	)
	
};

function yycgdmxaddsubmit_callback(data) {
	var result = getCallbackData(data);
	_alert(result);
	yyypmlquery();//提交完成刷新本页面
	parent.yycgdmxquery();
}



//工具栏
var toolbar = [{
	id : 'yycgdmxadd',
	text : '确认添加',
	iconCls : 'icon-add',
	handler : yycgdmxaddsubmit
	}];

var frozenColumns;

var columns = [ [{
	checkbox:true
},{
	field : 'ypxxid',
	hidden : true,
	formatter: function(value,row,index){
		return '<input type="hidden" name="yycgdmxs['+index+'].ypxxid" value="'+row.ypxxid+'" />';
	}
},{
	field : 'yycgdid',
	hidden : true,
	formatter: function(value,row,index){
		return '<input type="hidden" name="yycgdmxs['+index+'].yycgdid" value="${yycgdid}" />';
	}
},{
	field : 'usergysid',
	hidden : true,
	formatter: function(value,row,index){
		return '<input type="hidden" name="yycgdmxs['+index+'].usergysid" value="'+row.usergysid+'" />';
	}
},{
	field : 'bm',
	title : '流水号',
	width : 50
},{
	field : 'mc',
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
	field : 'scqymc',
	title : '生产企业',
	width : 100
},{
	field : 'spmc',
	title : '商品名称',
	width : 100
},{
	field : 'jyztmc',
	title : '交易状态',
	width : 60
},{
	field : 'usergysmc',
	title : '供货商',
	width : 100
},{
	field : 'control',
	title : '供货状态',
	width : 60,
	formatter: function(value,row,index){
		if (value){
			if(value == '1'){
				return '正常';
			}else{
				return '无法供货';
			}
		}else{
			return '无法供货';
		}
	}

}]];

function initGrid(){
	$('#yyypmllist').datagrid({
		title : '采购单列表',
		//nowrap : false,
		striped : true,
		//collapsible : true,
		url : '${baseurl}/cgd/yycgdmxadd_result.action',
		queryParams:{
			yycgdid:'${yycgdid}'
		},		
		//sortName : 'code',
		//sortOrder : 'desc',
		//remoteSort : false,
		//idField : 'id',
		//frozenColumns : frozenColumns,
		autoRowHeight:true,
		columns : columns,
		pagination : true,
		rownumbers : true,
		toolbar : toolbar,
		loadMsg:"",
		pageList:[15,30,50,100],
		onClickRow : function(index, field, value) {
					$('#yyypmllist').datagrid('unselectRow', index);
				}
		});

	}
	$(function() {
		initGrid();

	});

	function yyypmlquery() {
		
		var formdata = $("#yyypmlForm").serializeJson();
		//alert(formdata);
		$('#yyypmllist').datagrid('unselectAll');
		$('#yyypmllist').datagrid('load', formdata);
	}
</script>
</HEAD>
<BODY>

 <form id="yyypmlForm" name="yyypmlForm" action="${baseurl}/cgd/yycgdmxaddsubmit.action" method="post">
	<input type="hidden" name="indexs" id="indexs" />
	<input type="hidden" name="yycgdid" value="${yycgdid}" />
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
									<option value="${value.dictcode}" <c:if test="${value.dictcode =='1'}">selected</c:if> >${value.info}</option>
								</c:forEach>
							</select>
							
						</td>
						
				 		 <td class="left" height="25">供货状态：</td>
				  		<td>
				  		<select id="control" name="control" style="width:80px">
								<option value="">全部</option>
								<option value="1" selected>正常</option>
								<option value="2">无法供货</option>
						</select>
					
				  		</td>

				  		<td colspan=2>
				  		
						<a id="btn" href="#" onclick="yyypmlquery()" class="easyui-linkbutton" iconCls='icon-search'>查询</a>
				  		</td>
						
					</TR>
					
				</TBODY>
			</TABLE>
	   
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="yyypmllist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>

</BODY>
</HTML>

