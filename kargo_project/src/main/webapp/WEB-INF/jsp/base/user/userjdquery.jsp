<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>监督单位信息查询</title>
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
	var userjddel = function(id) {
		_confirm('您确定要执行删除操作吗?', null, function() {
			$("#userjddelid").val(id);
			jquerySubByFId('userjddelForm', userjddel_callback, null, "json");
		});
	};
	function userjddel_callback(data) {
		var result = getCallbackData(data);
		_alert(result);
		userjdquery();
	}
	var userjdedit = function(id) {
		//alert(id);
		var sendUrl = "${baseurl}/user/userjdedit.action?editid=" + id;
		createmodalwindow("修改监督单位信息", 800, 250, sendUrl);
	};

	
	function userjdview(id) {
		var sendUrl = "${baseurl}/user/userjdview.action?editid=" + id;
		createmodalwindow("查看监督单位信息", 800, 250, sendUrl);
	}
	var userjdadd = function() {
		//alert(id);
		var sendUrl = "${baseurl}/user/userjdedit.action";
		createmodalwindow("添加监督单位信息", 800, 250, sendUrl);
	};

	//工具栏
	var toolbar = [ {
		id : 'userjdadd',
		text : '添加',
		iconCls : 'icon-add',
		handler : userjdadd
	} ];

	var frozenColumns;

	var columns = [ [ /* {
		field : 'id',
		title : '',
		checkbox : true
	}, */ {
		field : 'mc',
		title : '单位名称',
		width : 180
	}, {
		field : 'dqmc',
		title : '管理地区',
		width : 130
	}, {
		field : 'dz',
		title : '地址',
		width : 200
	}, {
		field : 'yzbm',
		title : '邮政编码',
		width : 100
	}, {
		field : 'lxr',
		title : '联系人',
		width : 100
	}, {
		field : 'dh',
		title : '电话',
		width : 100
	}, {
		field : 'opt1',
		title : '修改',
		width : 60,
		formatter : function(value, row, index) {
			return '<a href=javascript:userjdedit(\'' + row.id + '\')>修改</a>';
		}
	}, {
		field : 'opt2',
		title : '删除',
		width : 60,
		formatter : function(value, row, index) {
			return '<a href=javascript:userjddel(\'' + row.id + '\')>删除</a>';
		}
	}, {
		field : 'opt3',
		title : '详细',
		width : 60,
		formatter : function(value, row, index) {
			return '<a href=javascript:userjdview(\'' + row.id + '\')>查看</a>';
		}
	} ] ];

	function initGrid() {
		$('#userjdlist').datagrid({
			title : '监督单位信息列表',
			//nowrap : false,
			striped : true,
			//collapsible : true,
			url : '${baseurl}/user/userjdquery_result.action',
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
				$('#userjdlist').datagrid('unselectRow', index);
			}
		});

	}
	$(function() {
		initGrid();
		tree= new createSyncTree("queryAreaTree_content","queryAreaTree","userjdCustom_dqmc","userjdCustom_dq",setting,null,null,"onClick");
	});

	function userjdquery() {

		var formdata = $("#userjdqueryForm").serializeJson();
		//alert(formdata);
		$('#userjdlist').datagrid('unselectAll');
		$('#userjdlist').datagrid('load', formdata);
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
	var tree;
	function showAreaTree() {
		tree.showMenu();
	}
</script>
</HEAD>
<BODY>
	<div id="userjdquery_div">
		<form id="userjdqueryForm" name="userjdqueryForm" method="post" action="${baseurl}/user/userjdquery_result.action">
			<TABLE class="table_search">
				<TBODY>
					<TR>
						<TD class="left">监督单位名称：</td>
						<td><INPUT type="text" name="userjdCustom.mc" /></TD>
						<TD class="left">管理地区：</TD>
						<td><INPUT type="text" id="userjdCustom_dqmc" readonly
							onclick="showAreaTree()" />
							<div id="queryAreaTree_content" class="zTreeDemoBackground left"
								style="z-index: 1; position: absolute; background-color: #ccc; overflow: scroll; HEIGHT: 300px; width: 200px; display: none;">
								<ul id="queryAreaTree" class="ztree"></ul>
							</div> <input type="hidden" name="userjdCustom.dq" id="userjdCustom_dq" />
						</td>
						<td colspan=4><a id="btn" href="#" onclick="userjdquery()"
							class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>
					</TR>


				</TBODY>
			</TABLE>
		</form>
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="userjdlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</div>

	<div id="userjdedit_div"></div>
	<form id="userjddelForm" name="userjddelForm"
		action="${baseurl}/user/userjddel.action" method="post">
		<input type="hidden" id="userjddelid" name="delid" />
	</form>

</BODY>
</HTML>

