<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${baseurl}/js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript" src="${baseurl}/js/zTree_v3/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${baseurl}/js/zTree_v3/jquery.ztree.excheck-3.5.js"></script>

		<script type="text/javascript">
		
	$(function (){
		//***********按钮**************
		$('#submitbtn').linkbutton({   
    		iconCls: 'icon-ok'  
		});  
		$('#closebtn').linkbutton({   
    		iconCls: 'icon-cancel'  
		});
		//**********表单校验*************
		$.formValidator.initConfig({
			formID : "userjdeditform",
			theme : "Default",
			onError : function(msg, obj, errorlist) {
				//alert(msg);
			}
		});
		//监督单位名称
		$("#userjd_mc").formValidator({
			onShow : "",
			onCorrect:"&nbsp;"
		}).inputValidator({
			min : 1,
			max : 100,
			onError : "请输入监督单位名称(最长50个字符)"
		});
		//监督单位管理地区
		$("#userjd_dq").formValidator({
			onShow : "",
			onCorrect:"&nbsp;"
		}).inputValidator({
			min : 1,
			max : 100,
			onError : "请选择监督单位管理地区"
		});
		
	});
	function userjdsave(){
		if($.formValidator.pageIsValid()){
			jquerySubByFId('userjdeditform',userjdsave_callback,null,"json");
		}

	}
	function userjdsave_callback(data){
		var result = getCallbackData(data);
		var type = result.type;
		_alert(result);
		/* if (TYPE_RESULT_SUCCESS == type) {
			parent.userjdquery();
			parent.closemodalwindow();
		} 	 */
	}
	
	//******区域树**********

		var setting = {
				view: {
					selectedMulti: false
				},
				data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentid",
						rootPId: 0
					}
				},
				callback: {
					onClick: null
				}
			};
	
			var zNodes;
			var tree;
			function showAreaTree() {
				tree.showMenu();
			}

			$(document).ready(function(){
				//通过ajax获取树的结点
				var sendUrl = "${baseurl}/areaload/sync.action?areaid=1.";
				var ajaxOption = new AjaxOption();
					ajaxOption._initPostRequest(true,sendUrl,"pame","json");
					_ajaxPostRequest(ajaxOption, '', areaload_callback);
					tree= new createSyncTree("areaTreeContent","areaTree","userjd_dqmc","userjd_dq",setting,zNodes,null,"onClick");
			});
			
			function areaload_callback(redata){
				try{
				zNodes = redata;
				}catch(e){
					//alert(e);
				}
				return ;
			}
	</script>
 </HEAD>
<BODY>
<form id="userjdeditform" name="userjdeditform" action="${baseurl}/user/userjdsave.action" method="post">
<input type="hidden" name="userjd.id" value="${userjd.id}"/>
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
								<input type="text" id="userjd_mc" name="userjd.mc" value="${userjd.mc}"   />
								</div>
								<div id="userjd_mcTip"></div>
								</TD>
								<TD height=30 width="15%" align=right>管理地区：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="userjd_dqmc" readonly value="${userjddqmc}" onclick="showAreaTree()"/>
								<input type="hidden" id="userjd_dq" name="userjd.dq" value="${userjd.dq}"/>
								</div>
								<div id="areaTreeContent" class="zTreeDemoBackground left ztree_customcss" >
									<ul id="areaTree" class="ztree"></ul>
								</div>
								<div id="userjd_dqTip"></div>
								</TD>
							</TR>
							
							
							<TR>
								<TD height=30 width="15%" align=right >通讯地址：</TD>
								<TD class=category width="35%">
								<input type="text" name="userjd.dz" value="${userjd.dz}"/>
								</TD>
								<TD height=30 width="15%" align=right>邮政编码：</TD>
								<TD class=category width="35%">
								<input type="text" name="userjd.yzbm" value="${userjd.yzbm}"/>
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >电话：</TD>
								<TD class=category width="35%">
								<input type="text" name="userjd.dh" value="${userjd.dh}"/>
								</TD>
								<TD height=30 width="15%" align=right>传真：</TD>
								<TD class=category width="35%">
								<input type="text" name="userjd.cz" value="${userjd.cz}"/>
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >email：</TD>
								<TD class=category width="35%">
								<input type="text" name="userjd.dzyx" value="${userjd.dzyx}"/>
								</TD>
								<TD height=30 width="15%" align=right>网址：</TD>
								<TD class=category width="35%">
								<input type="text" name="userjd.wz" value="${userjd.wz}"/>
								</TD>
							</TR>
							<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn" href="#" onclick="userjdsave()">提交</a>
								<a id="closebtn" href="#" onclick="parent.closemodalwindow()">关闭</a>
							  </td>
							</tr>
						
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>

</BODY>
</HTML>

