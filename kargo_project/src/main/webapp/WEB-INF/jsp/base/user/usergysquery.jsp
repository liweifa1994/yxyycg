<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>供应商信息查询</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${baseurl}/js/zTree_v3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript"
	src="${baseurl}/js/zTree_v3/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${baseurl}/js/zTree_v3/jquery.ztree.excheck-3.5.js"></script>

<script type="text/javascript">
	var usergysdel = function(id) {
		_confirm('您确定要执行删除操作吗?', null, function() {
			$("#usergysdelid").val(id);
			jquerySubByFId('usergysdelForm', usergysdel_callback, null, "json");
		});
	};
	function usergysdel_callback(data) {
		var result = getCallbackData(data);
		_alert(result);
		usergysquery();
	}
	var usergysedit = function(id) {
		//alert(id);
		var sendUrl = "${baseurl}/user/usergysedit.action?editid=" + id;
		createmodalwindow("修改供应商信息", 800, 480, sendUrl);

		
	};


	function usergysview(id) {
		var sendUrl = "${baseurl}/user/usergysview.action?editid=" + id;
		createmodalwindow("查看供应商信息", 800, 480, sendUrl);
	}
	var usergysadd = function() {
		//alert(id);
		var sendUrl = "${baseurl}/user/usergysedit.action";
		createmodalwindow("添加供应商信息", 800, 480, sendUrl);
	};

	//工具栏
	var toolbar = [ {
		id : 'usergysadd',
		text : '添加',
		iconCls : 'icon-add',
		handler : usergysadd
	} ];

	var frozenColumns;

	var columns = [ [ /* {
		field : 'id',
		title : '',
		checkbox : true
	}, */ {
		field : 'mc',
		title : '企业名称',
		width : 180
	}, {
		field : 'lxr',
		title : '联系人',
		width : 130
	}, {
		field : 'dh',
		title : '电话',
		width : 100
	}, {
		field : 'lxdz',
		title : '联系地址',
		width : 100
	}, {
		field : 'useryyghdq',
		title : '供货地区',
		width : 250
	}, {
		field : 'opt1',
		title : '修改',
		width : 60,
		formatter : function(value, row, index) {
			return '<a href=javascript:usergysedit(\'' + row.id + '\')>修改</a>';
		}
	}, {
		field : 'opt2',
		title : '删除',
		width : 60,
		formatter : function(value, row, index) {
			return '<a href=javascript:usergysdel(\'' + row.id + '\')>删除</a>';
		}
	}, {
		field : 'opt3',
		title : '详细',
		width : 60,
		formatter : function(value, row, index) {
			return '<a href=javascript:usergysview(\'' + row.id + '\')>查看</a>';
		}
	} ] ];

	function initGrid() {
		$('#usergyslist').datagrid({
			title : '供应商信息列表',
			//nowrap : false,
			striped : true,
			//collapsible : true,
			url : '${baseurl}/user/usergysquery_result.action',
			//sortName : 'code',
			//sortOrder : 'desc',
			//remoteSort : false,
			idField : 'id',
			//frozenColumns : frozenColumns,
			columns : columns,
			pagination : true,
			rownumbers : true,
			toolbar : toolbar,
			loadMsg : "",
			pageList : [ 15, 30, 50, 100 ],
			onClickRow : function(index, field, value) {
				$('#usergyslist').datagrid('unselectRow', index);
			}
		});

	}
	$(function() {
		initGrid();

	});

	function usergysquery() {

		var formdata = $("#usergysqueryForm").serializeJson();
		//alert(formdata);
		$('#usergyslist').datagrid('unselectAll');
		$('#usergyslist').datagrid('load', formdata);
	}

	//******区域树**********

	var setting = {
		view : {
			selectedMulti : false
		},
		async : {
			enable : true,
			url : "${baseurl}/areaload/async.action",
			datatype : "json",
			autoParam : [ "id=areaid", "name=n", "level=lv" ],
			otherParam : {
				"otherParam" : "zTreeAsyncTest"
			}
		},
		callback : {
			onClick : null
		}
	};
	
	function showAreaTree() {
		var tree= new createSyncTree("queryAreaTree_content","queryAreaTree","usergysCustom_dqmc","usergysCustom_dq",setting);
		tree.init();
	}
</script>
</HEAD>
<BODY>
	<div id="usergysquery_div">
		<form id="usergysqueryForm" name="usergysqueryForm" method="post" action="${baseurl}/user/usergysquery_result.action">
			<TABLE class="table_search">
				<TBODY>
					<TR>
						<TD class="left">企业名称：</td>
						<td><INPUT type="text" name="usergysCustom.mc" /></TD>
						
						<td colspan=4><a id="btn" href="#" onclick="usergysquery()"
							class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>
					</TR>


				</TBODY>
			</TABLE>
		</form>
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="usergyslist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</div>

	<div id="usergysedit_div"></div>
	<form id="usergysdelForm" name="usergysdelForm"
		action="${baseurl}/user/usergysdel.action" method="post">
		<input type="hidden" id="usergysdelid" name="delid" />
	</form>

</BODY>
</HTML>

