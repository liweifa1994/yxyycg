<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html> 
<head>
<title>采购药品入库</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">

var yycgdrksubmit = function(){
	_confirm('您确定要入库选择的药品吗?',null,
	  function(){
		
		var indexs = [];//提交记录的序号
		var rows = $('#yycgdmxlist').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			var index=$('#yycgdmxlist').datagrid('getRowIndex',rows[i]);
			indexs.push(index);
		}
		if(rows.length>0){
			$("#indexs").val(indexs.join(','));
			jquerySubByFId('yycgdrkForm', yycgdrk_callback, null);
		}else{
			alert_warn("请选择要入库的药品");
		}
		
	  }
	)
	
};

function yycgdrk_callback(data) {
	var result = getCallbackData(data);
	_alert(result);

}



//工具栏

var toolbar = [{
	id : 'yycgdrksubmit',
	text : '确认入库',
	iconCls : 'icon-add',
	handler : yycgdrksubmit
	}];

var frozenColumns;

var columns = [ [ {
	checkbox:true
},{
	field : 'ypxxid',
	hidden : true,
	formatter: function(value,row,index){
		return '<input type="hidden" name="yycgdrkmxs['+index+'].ypxxid" value="'+row.ypxxid+'" />';
	}
},{
	field : 'yycgdid',
	hidden : true,
	formatter: function(value,row,index){
		return '<input type="hidden" name="yycgdrkmxs['+index+'].yycgdid" value="'+row.yycgdid+'" />';
	}
},
 {
	field : 'useryymc',
	title : '医院名称',
	width : 100
},{
	field : 'yycgdbm',
	title : '采购单编号',
	width : 70
},{
	field : 'cjtime',
	title : '建单时间',
	width : 70,
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
	width : 50
},{
	field : 'gg',
	title : '规格',
	width : 70
},{
	field : 'zhxs',
	title : '转换<br>系数',
	width : 20
},{
	field : 'zbjg',
	title : '中标价',
	width : 50
},{
	field : 'jyjg',
	title : '交易价',
	width : 40
},{
	field : 'cgl',
	title : '采购量',
	width : 40
},{
	field : 'cgje',
	title : '采购金额',
	width : 40
},{
	field : 'rkl',
	title : '入库量',
	width : 50,
	formatter:function(value,row,index){
		   return '<input type="text" name="yycgdrkmxs['+index+'].rkl" value="'+(value?value:"")+'" />';
	}
},{
	field : 'rkje',
	title : '入库金额',
	width : 60
}
,{
	field : 'rkdh',
	title : '发票号或入库单号',
	width : 90,
	formatter:function(value,row,index){
		   return '<input type="text" name="yycgdrkmxs['+index+'].rkdh" value="'+(value?value:"")+'" />';
	}
},{
	field : 'ypph',
	title : '药品批号',
	width : 70,
	formatter:function(value,row,index){
		   return '<input type="text" name="yycgdrkmxs['+index+'].ypph" value="'+(value?value:"")+'" />';
	}
},{
	field : 'ypyxq',
	title : '药品有效期(年)',
	width : 70,
	formatter:function(value,row,index){
		   return '<input type="text" name="yycgdrkmxs['+index+'].ypyxq" value="'+(value?value:"")+'" />';
	}
}
,{
	field : 'cgztmc',
	title : '采购状态', 
	width : 60
}]];

function initGrid(){
	$('#yycgdmxlist').datagrid({
		title : '采购单列表',
		//nowrap : false,
		striped : true,
		//collapsible : true,
		url : '${baseurl}/cgd/yycgdrkquery_result.action',
		queryParams:{
			year:'${year}'
		},
		//sortName : 'code',
		//sortOrder : 'desc',
		//remoteSort : false,
		//idField : 'id',//如果值不是主键则影响获取checkbox选中个数
		//frozenColumns : frozenColumns,
		columns : columns,
		autoRowHeight:true,
		pagination : true,
		rownumbers : true,
		toolbar : toolbar,
		loadMsg:"",
		pageList:[15,30,50,100]/* ,
		onClickRow : function(index, field, value) {
					$('#yycgdmxlist').datagrid('unselectRow', index);
				} */
		});

	}
	$(function() {
		initGrid();
		
	});

	function yycgdmxquery() {
		var formdata = $("#yycgdrkForm").serializeJson();
		//alert(formdata);
		$('#yycgdmxlist').datagrid('unselectAll');
		$('#yycgdmxlist').datagrid('load', formdata);
	}
	
	$(function(){
		//加载年
		businessyearlist('businessyear');
	
	});
</script>
</HEAD>
<BODY>
    <form id="yycgdrkForm" name="yycgdrkForm" method="post" action="${baseurl}/cgd/yycgdrk_submit.action">
			<input type="hidden" name="indexs" id="indexs" />
			<TABLE  class="table_search">
			<TBODY>
				<TR>
					<TD class="left">年份(如2014)：</TD>
					<td>
						<select id="businessyear" name="year"></select>
					</td>
					
					<TD class="left">采购单编号：</td>
					<td><INPUT type="text" name="yycgdCustom.bm" /></TD>
					<TD class="left">采购单名称：</TD>
					<td><INPUT type="text" name="yycgdCustom.mc" /></td>
					<TD class="left">采购时间：</TD>
					<td><INPUT id="yycgdCustom.kscgdate"
						name="yycgdCustom.kscgdate"
						onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						style="width: 80px" />-- <INPUT id="yycgdCustom.jscgdate"
						name="yycgdCustom.jscgdate"
						onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						style="width: 80px" /></td>
				</TR>
				<TR>
					
					
					<TD class="left">流水号：</TD>
					<td><INPUT type="text" name="ypxxCustom.bm" /></td>
					<TD class="left">通用名：</td>
					<td><INPUT type="text" name="ypxxCustom.mc" /></TD>
					
					<TD class="left">入库时间：</TD>
					<td><INPUT id="yycgdCustom.startrktime"
						name="yycgdCustom.startrktime"
						onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						style="width: 80px" />-- <INPUT id="yycgdCustom.endrktime"
						name="yycgdCustom.endrktime"
						onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						style="width: 80px" /></td>
						<TD class="left">药品批号：</td>
					<td><INPUT type="text" name="yycgdCustom.ypph" /></TD>
				</tr>
				<tr>

					
					<TD class="left">药品有效期(年)：</td>
					<td><INPUT type="text" name="yycgdCustom.ypyxq" /></TD>
					<TD class="left">发票号或入库单号：</td>
					<td><INPUT type="text" name="yycgdCustom.rkdh" /></TD>
					<TD class="left">采购状态：</td>
					<td>
					已发货

					</TD>
					<td colspan=2>
					<a id="btn" href="#" onclick="yycgdmxquery()"
						class="easyui-linkbutton" iconCls='icon-search'>查询</a>
					</TD>
				</tr>
			
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

