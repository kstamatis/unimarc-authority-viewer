
<%@ page import="gr.kstamatis.nlg.domain.Record" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'record.label', default: 'Record')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		
		<!--XSLT style-->
        <style>
            
          h1.label {
            font-size: 13pt;
            /*font-family : "Palatino Linotype";*/
            color : Black}
          
          a.header {
            font-size : 12pt;
            /*font-family : "Palatino Linotype";*/
            color: Black;
            text-decoration : none;}
          
          a.tagData {
            font-size : 12pt;
            /*font-family : "Palatino Linotype";*/
            color: Maroon;
            text-decoration : none;
            font: bold; }
          
          a.tagControl {
            font-size : 12pt;
            /*font-family : "Palatino Linotype";*/
            color: Maroon;
            text-decoration : none;
            font: bold;}	
          
          a.indicators{
            font-size : 12pt;
            /*font-family : "Palatino Linotype";*/
            color : Red;
            text-decoration : none;}
            
          a.sfCode{
            font-size : 12pt;
            /*font-family : "Palatino Linotype";*/
            color : Blue;
            text-decoration : none;
            font: bold;}
            
          a.sfValueNormal{
            font-size : 12pt;
            /*font-family : "Palatino Linotype";*/
            color : Black;
            text-decoration : none;}
            
          a.sfValueHighlighted{
          	font-size : 12pt;
         	/*font-family : "Palatino Linotype";*/
         	color : White;
          	text-decoration : none;
         	background-color : Navy;}
          
           tr.tablerowon{
           border-top: 1px solid;
            background-color :#FFFFE0;}
            
        </style>
        
	</head>
	<body>
		<a href="#show-record" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="Αρχική"/></a></li>
			</ul>
		</div>
		<div id="show-record" class="content scaffold-show" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list record">
				
				${marc}
				
			</ol>
		</div>
	</body>
</html>
