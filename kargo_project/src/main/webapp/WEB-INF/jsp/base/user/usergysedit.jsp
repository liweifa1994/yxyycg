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
			formID : "usergyseditform",
			theme : "Default",
			onError : function(msg, obj, errorlist) {
				//alert(msg);
			}
		});
		//医院名称
		$("#usergys_mc").formValidator({
			onShow : "",
			onCorrect:"&nbsp;"
		}).inputValidator({
			min : 1,
			max : 100,
			onError : "请输入医院名称(最长50个字符)"
		});
		//医院地区
		$("#usergys_dq").formValidator({
			onShow : "",
			onCorrect:"&nbsp;"
		}).inputValidator({
			min : 1,
			max : 100,
			onError : "请选择医院所在地区"
		});
		
	});
	function usergyssave(){
		if($.formValidator.pageIsValid()){
			jquerySubByFId('usergyseditform',usergyssave_callback,null,"json");
		}

	}
	function usergyssave_callback(data){
		var result = getCallbackData(data);
		var type = result.type;
		_alert(result);
		/* if (TYPE_RESULT_SUCCESS == type) {
			parent.usergysquery();
			parent.closemodalwindow();
		} 	 */
	}
	
	//******区域树**********

		var setting = {
				view: {
					selectedMulti: false
				},
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
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
				var sendUrl = "${baseurl}/areaload/sync.action?areaid=1.&arealevel=2";
				var ajaxOption = new AjaxOption();
					ajaxOption._initPostRequest(false,sendUrl,"pame","json");
					_ajaxPostRequest(ajaxOption, '', areaload_callback);	
					tree = new createSyncTree("areaTreeContent","areaTree","usergysghdq","usergysghdqid",setting,zNodes,1,"onCheck",'${usergysghdqid}');	
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
<form id="usergyseditform" name="usergyseditform" action="${baseurl}/user/usergyssave.action" method="post">
<input type="hidden" name="usergys.id" value="${usergys.id}"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;供应商信息</TD>
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
								<TD height=30 width="15%" align=right >企业名称：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="usergys_mc" name="usergys.mc" value="${usergys.mc}"   />
								</div>
								<div id="usergys_mcTip"></div>
								</TD>
								<TD height=30 width="15%" align=right> 供货地区：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="usergysghdq" value="${usergysghdq}" onclick="showAreaTree()"/>
								<input type="hidden" id="usergysghdqid" name="usergysghdqid" value="${usergysghdqid}"/>
								</div>
								<div id="areaTreeContent" class="zTreeDemoBackground left ztree_customcss" >
									<ul id="areaTree" class="ztree"></ul>
								</div>
								<div id="usergys_dqTip"></div>
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >经营范围：</TD>
								<TD class=category width="35%">
								<input type="text" name="usergys.jyfw" value="${usergys.jyfw}"/>
								</TD>
								<TD height=30 width="15%" align=right>注册地址(中文)：</TD>
								<TD class=category width="35%">
								<input type="text" name="usergys.zcdz" value="${usergys.zcdz}"/>
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >注册资金(万元)：</TD>
								<TD class=category width="35%">
								<input type="text" name="usergys.zczj" value="${usergys.zczj}"/>
								</TD>
								<TD height=30 width="15%" align=right>固定资产(万元)：</TD>
								<TD class=category width="35%">
								<input type="text" name="usergys.gdzc" value="${usergys.gdzc}"/>
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >联系人：</TD>
								<TD class=category width="35%">
								<input type="text" name="usergys.lxr" value="${usergys.lxr}"/>
								</TD>
								<TD height=30 width="15%" align=right>联系电话：</TD>
								<TD class=category width="35%">
								<input type="text" name="usergys.dh" value="${usergys.dh}"/>
								</TD>
							</TR>
							
							<TR>
								<TD height=30 width="15%" align=right >email：</TD>
								<TD class=category width="35%">
								<input type="text" name="usergys.dzyx" value="${usergys.dzyx}"/>
								</TD>
								<TD height=30 width="15%" align=right>网址：</TD>
								<TD class=category width="35%">
								<input type="text" name="usergys.wz" value="${usergys.wz}"/>
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >联系地区：</TD>
								<TD class=category width="35%">
								<input type="text" name="usergys.lxdz" value="${usergys.lxdz}"/>
								</TD>
								<TD height=30 width="15%" align=right>邮政编码：</TD>
								<TD class=category width="35%">
								<input type="text" name="usergys.yzbm" value="${usergys.yzbm}"/>
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >企业简介：</TD>
								<TD class=category colspan=3>
								<textarea rows="10" cols="80" name="usergys.jj">${usergys.jj}</textarea>
								</TD>
								
							</TR>
							<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn" href="#" onclick="usergyssave()">提交</a>
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

