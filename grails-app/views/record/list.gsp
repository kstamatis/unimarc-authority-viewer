
<%@ page import="gr.kstamatis.nlg.domain.Record" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'record.label', default: 'Record')}" />
		<title>Αρχείο Επικεφαλίδων ΕΒΕ: Πλοήγηση</title>
	</head>
	<body>
		<a href="#list-record" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="Αρχική"/></a></li>
			</ul>
		</div>
		<div id="list-record" class="content scaffold-list" role="main">
			<h1>Λίστα εγγραφών</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:set var="mytag" value="${tag}"></g:set>
			
			<table>
				<thead>
					<tr>
					<g:if test="${tag}">
						<g:sortableColumn property="recordId" title="${message(code: 'record.recordId.label', default: 'Κωδικός Εγγραφής')}"  params="[tag: mytag]"/>
					
						<g:sortableColumn property="tag" title="${message(code: 'record.tag.label', default: 'Τύπος')}"  params="[tag: mytag]"/>
					
						<g:sortableColumn property="displayValue" title="${message(code: 'record.displayValue.label', default: 'Τιμή')}"  params="[tag: mytag]"/>
					</g:if>
					<g:else>
						<g:sortableColumn property="recordId" title="${message(code: 'record.recordId.label', default: 'Κωδικός Εγγραφής')}" />
					
						<g:sortableColumn property="tag" title="${message(code: 'record.tag.label', default: 'Τύπος')}" />
					
						<g:sortableColumn property="displayValue" title="${message(code: 'record.displayValue.label', default: 'Τιμή')}" />
					
					</g:else>
					</tr>
				</thead>
				<tbody>
				<g:each in="${recordInstanceList}" status="i" var="recordInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
					<td><g:link action="show" id="${recordInstance.id}">${fieldValue(bean: recordInstance, field: "recordId")}</g:link></td>
					
					<td>${fieldValue(bean: recordInstance, field: "tag")}</td>
					
					<td>${recordInstance.displayValue}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:if test="${tag}">
					<g:paginate total="${recordInstanceTotal}" params="[tag: mytag]" />
				</g:if>
				<g:else>
					<g:paginate total="${recordInstanceTotal}" />
				</g:else>
			</div>
		</div>
	</body>
</html>
