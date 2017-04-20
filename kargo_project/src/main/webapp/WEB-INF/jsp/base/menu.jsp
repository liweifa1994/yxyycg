<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<link href="${baseurl}/styles/menu.css" rel="stylesheet" type="text/css">
</head>
<body>

<DIV class=menu id=MenuContainer>


<c:forEach items="${menulist}" var="menuindex">
 <H4>${menuindex.modulename}</H4>
      <DIV style="DISPLAY: none">
      <c:forEach items="${menuindex.menulist}" var="menuindex2">
         <P id="menu_${menuindex2.id}"><a href="${baseurl}/${menuindex2.moduleurl}" target="contentFrame" onclick=choosemenu('menu_${menuindex2.id}')>${menuindex2.modulename}</a></P>
      </c:forEach>
     </DIV>

</c:forEach>


<script src="${baseurl}/scripts/front_menu.js"></script>
 <SCRIPT>
   var active;
   function choosemenu(id){
     document.getElementById(id).className="pick";
     if(active){
	    document.getElementById(active).className="";
	 }
	 active=id;
   }
</SCRIPT>
</body>
</html>

