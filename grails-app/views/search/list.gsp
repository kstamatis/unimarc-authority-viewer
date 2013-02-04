
<%@ page import="gr.kstamatis.nlg.domain.Record" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'record.label', default: 'Record')}" />
		<title>Αρχείο Επικεφαλίδων ΕΒΕ: Αναζήτηση</title>
	</head>
	<body>
		<a href="#list-record" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="Αρχική"/></a></li>
			</ul>
		</div>
		<div style="padding: 20px 20px 40px;">
			<g:form controller="search" action="search">
					Αναζήτηση&nbsp;&nbsp;<g:select name="searchtype" value="${searchtype }" from="['Όλες οι κατηγορίες','Ονόματα Φυσικών Προσώπων', 'Συλλογικά Όργανα', 
					'Θεματικές Επικεφαλίδες', 'Γεωγραφικά Ονόματα', 'Ομοιόμορφοι Τίτλοι', 'Οικογενειακά Ονόματα',
					'Όνομα / Τίτλος ως Θέμα', 'Σειρές']" keys="['-1', '200', '210', '250', '215', '230', '220', '240', '225']"></g:select>
					<g:textField name="query" value="${query }"/>
					<g:submitButton name="submit" value="Go!"/>
				</g:form>
		</div>
		<div id="list-record" class="content scaffold-list" role="main">
			<h1>Αποτελέσματα (Σύνολο: ${resultListTotal })</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<table>
				<thead>
					<tr>
						<th>Κωδικός Εγγραφής</th>
					
						<th>Τύπος</th>
					
						<th>Τιμή</th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${resultList}" status="i" var="recordInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
					<g:set var="recid" value="${recordInstance.get("c001")}"></g:set>
					<td><g:link controller="record" action="showrecid" params="[recid: recid]">${recordInstance.get("c001")}</g:link></td>
					
					<td>${recordInstance.get("tag")}</td>
					
					<td>${recordInstance.get("displayvalue")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
					<g:paginate total="${resultListTotal}" params="[query: query, searchtype: searchtype]" />
			</div>
		</div>
	</body>
</html>
