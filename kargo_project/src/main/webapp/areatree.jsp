<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="http://localhost:8080/caigou2.0-bss/js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="http://localhost:8080/caigou2.0-bss/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="http://localhost:8080/caigou2.0-bss/js/zTree_v3/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="http://localhost:8080/caigou2.0-bss/js/zTree_v3/jquery.ztree.excheck-3.5.js"></script>

<script type="text/javascript" src="http://localhost:8080/caigou2.0-bss/js/jquery.form.min.js"></script>

<script type="text/javascript" src="http://localhost:8080/caigou2.0-bss/js/custom.jquery.form.js"></script>
<script type="text/javascript" src="http://localhost:8080/caigou2.0-bss/js/custom.box.main.js"></script>
<script type="text/javascript" src="http://localhost:8080/caigou2.0-bss/js/jquery.ajax.custom.extend.js"></script>

<SCRIPT type="text/javascript">

var setting = {
		check: {
			enable: true,
			chkboxType: {"Y":"ps", "N":"ps"}
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "areaid",
				pIdKey: "parentid",
				name:"areaname",
				rootPId: -1,
			}
		},
		callback: {
			beforeClick: beforeClick,
			onCheck: onCheck
		}
	};

	var zNodes =[
		{id:1, pId:0, name:"北京"},
		{id:2, pId:0, name:"天津"},
		{id:3, pId:0, name:"上海"},
		{id:6, pId:0, name:"重庆"},
		{id:4, pId:0, name:"河北省", open:true, nocheck:true},
		{id:41, pId:4, name:"石家庄"},
		{id:42, pId:4, name:"保定"},
		{id:43, pId:4, name:"邯郸"},
		{id:44, pId:4, name:"承德"},
		{id:5, pId:0, name:"广东省", open:true, nocheck:true},
		{id:51, pId:5, name:"广州"},
		{id:52, pId:5, name:"深圳"},
		{id:53, pId:5, name:"东莞"},
		{id:54, pId:5, name:"佛山"},
		{id:6, pId:0, name:"福建省", open:true, nocheck:true},
		{id:61, pId:6, name:"福州"},
		{id:62, pId:6, name:"厦门"},
		{id:63, pId:6, name:"泉州"},
		{id:64, pId:6, name:"三明"}
	 ];

	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	
	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getCheckedNodes(true),
		v = "";
		vId = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			vId += nodes[i].id + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (vId.length > 0 ) vId = vId.substring(0, vId.length-1);
		var cityObj = $("#citySel");
		cityObj.attr("value", v);
		
		var cityIdObj = $("#citySelId");
		cityIdObj.attr("value", vId);
	}

	function showMenu() {
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}

	$(document).ready(function(){
		//通过ajax获取树的结点
		var sendUrl = "http://localhost:8080/yycg/areaload/sync.action";
		var ajaxOption = new AjaxOption();
			ajaxOption._initPostRequest(true,sendUrl,"pame","json");
			_ajaxPostRequest(ajaxOption, '', areaload_callback);

			
	});
	
	function areaload_callback(redata){
		/* $('#ypxxquery_div').css("display","none");
		$("#ypxximport_div").css("display","block");
		$("#ypxximport_div").html(redata); */
		try{
		//alert(redata);
		zNodes = redata;
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}catch(e){
			alert(e);
		}
		return ;
	}
	</SCRIPT>
</head>
<body>
<input id="citySel" type="text" readonly value="" style="width:1200px;"  />
<input id="citySelId" type="text" readonly value="" style="width:1200px;"  />
<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>




</body>
</html>