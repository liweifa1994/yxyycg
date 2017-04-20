<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html> 
<head>
<title>医院采购单审核</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">

var yycgdreviewsubmit = function(){
	_confirm('您确定要提交采购单的审核结果吗?',null,
	  function(){
		
		var indexs = [];//提交记录的序号
		var rows = $('#yycgdlist').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			var index=$('#yycgdlist').datagrid('getRowIndex',rows[i]);
			indexs.push(index);
		}
		if(rows.length>0){
			$("#indexs").val(indexs.join(','));
			jquerySubByFId('yycgdqueryForm', yycgdreview_callback, null);
		}else{
			alert_warn("请选择要审核的采购单");
		}
		
	  }
	)
	
};

function yycgdreview_callback(data) {
	var result = getCallbackData(data);
	_alert(result);
	yycgdquery();
}


function yycgdinfo(id){
	var sendUrl = "${baseurl}/cgd/yycgdinfo.action?yycgdid="+id;
	parent.opentabwindow(bm+'采购单查看',sendUrl);
}


//工具栏

var toolbar = [{
	id : 'yycgdreviewsubmit',
	text : '提交审核结果',
	iconCls : 'icon-add',
	handler : yycgdreviewsubmit
	}];

var frozenColumns;

var columns = [ [ {
	checkbox:true
},
 {
	field : 'zt',
	title : '审核结果',
	width : 120,
	formatter : function (value,row,index){
		return '<select  name="yycgds['+index+'].zt" style="width:100px">'
				+'<option value="" checked></option>'
				+'<option value="3">审核通过</option>'
				+'<option value="4">审核不通过</option>' 
				+'</select>';
	}
	
},
 {
	field : 'shyj',
	title : '审核意见',
	width : 100,
	formatter : function (value,row,index){
		return '<input type="text" name="yycgds['+index+'].shyj" value="'+(value?value:"")+'" />';
	}
},
 {
	field : 'id',
	hidden : true,
	formatter : function (value,row,index){
		return '<input type="text" name="yycgds['+index+'].id" value="'+(row.id)+'" />';
	}
},
 {
	field : 'useryymc',
	title : '医院名称',
	width : 100
},{
	field : 'bm',
	title : '采购单编号',
	width : 80
},{
	field : 'mc',
	title : '采购单名称',
	width : 150
},{
	field : 'cjtime',
	title : '建单时间',
	width : 80,
	formatter: function(value,row,index){
		if(value){
			try{
			var date = new Date(value);
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+"-"+m+"-"+d;
			}catch(e){
				alert(e);
			}
		}
		
	}
},{
	field : 'xgtime',
	title : '修改时间',
	width : 80,
	formatter: function(value,row,index){
		if(value){
			try{
			var date = new Date(value);
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+"-"+m+"-"+d;
			}catch(e){
				alert(e);
			}
		}
		
	}
},{
	field : 'tjtime',
	title : '提交时间',
	width : 80,
	formatter: function(value,row,index){
		if(value){
			try{
			var date = new Date(value);
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+"-"+m+"-"+d;
			}catch(e){
				alert(e);
			}
		}
		
	}
},{
	field : 'yycgdztmc',
	title : '采购单<br>状态', 
	width : 60
},{
	field : 'opt3',
	title : '查看',
	width : 60,
	formatter:function(value, row, index){
		return '<a href=javascript:yycgdinfo(\''+row.id+'\')>查看</a>';
	}
}]];

function initGrid(){
	$('#yycgdlist').datagrid({
		title : '采购单列表',
		//nowrap : false,
		striped : true,
		//collapsible : true,
		url : '${baseurl}/cgd/yycgdreview_result.action',
		queryParams:{
			year:'${year}'
		},
		//sortName : 'code',
		//sortOrder : 'desc',
		//remoteSort : false,
		idField : 'id',//采购单id
		//frozenColumns : frozenColumns,
		columns : columns,
		autoRowHeight:true,
		pagination : true,
		rownumbers : true,
		toolbar : toolbar,
		loadMsg:"",
		pageList:[15,30,50,100],
		onClickRow : function(index, field, value) {
					$('#yycgdlist').datagrid('unselectRow', index);
				}
		});

	}
	$(function() {
		initGrid();
		
	});

	function yycgdquery() {
		var formdata = $("#yycgdqueryForm").serializeJson();
		//alert(formdata);
		$('#yycgdlist').datagrid('unselectAll');
		$('#yycgdlist').datagrid('load', formdata);
	}
	$(function(){
		//加载年
		businessyearlist('businessyear');
	});
</script>
</HEAD>
<BODY>
    <form id="yycgdqueryForm" name="yycgdqueryForm" method="post" action="${baseurl}/cgd/yycgdreviewsubmit.action">
			<input type="hidden" name="indexs" id="indexs" />
			<TABLE  class="table_search">
				<TBODY>
					<TR>
						<TD class="left">年度：</td>
						<td><select id="businessyear" name="year"></TD>
						<TD class="left">采购单编号：</td>
						<td><INPUT type="text"  name="yycgdCustom.bm" /></TD>
						<TD class="left">采购单名称：</TD>
						<td ><INPUT type="text" name="yycgdCustom.mc" /></td>
						
						
					</TR>
					<TR> 
					    <TD class="left">采购单状态：</TD>
						<td >
							<select id="yycgdCustom.zt" name="yycgdCustom.zt" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${cgdztList}" var="value">
									<option value="${value.dictcode}">${value.info}</option>
								</c:forEach>
							</select>
						</td>
						<TD class="left">医院名称：</TD>
						<td ><INPUT type="text" name="yycgdCustom.useryymc" /></td>
						<TD class="left">采购时间：</TD>
						<td >
						 <INPUT id="yycgdCustom.kscgdate"
							name="yycgdCustom.kscgdate" 
							 onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:80px"/>--
					 <INPUT id="yycgdCustom.jscgdate" 
							name="yycgdCustom.jscgdate"
							 onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:80px"/>
							
						</td>
						<TD colspan=2>
							<a id="btn" href="#" onclick="yycgdquery()" class="easyui-linkbutton" iconCls='icon-search'>查询</a>
						</TD>
						
					</tr>
					
				</TBODY>
			</TABLE>
	   
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="yycgdlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		 </form>

<form id="yycgddeleteForm" name="yycgddeleteForm" action="${baseurl}/cgd/yycgddelete.action">
<input type="hidden" id="cgddeleteid" name="cgddeleteid" />
</form>

</BODY>
</HTML>

