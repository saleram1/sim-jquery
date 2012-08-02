
<%@ page import="com.mercadolibre.apps.sim.ItemImportSource" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'itemImportSource.label', default: 'ItemImportSource')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-itemImportSource" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-itemImportSource" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="description" title="${message(code: 'itemImportSource.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="site" title="${message(code: 'itemImportSource.site.label', default: 'Site')}" />
					
						<g:sortableColumn property="category" title="${message(code: 'itemImportSource.category.label', default: 'Category')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'itemImportSource.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="fileAttachment" title="${message(code: 'itemImportSource.fileAttachment.label', default: 'File Attachment')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${itemImportSourceInstanceList}" status="i" var="itemImportSourceInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${itemImportSourceInstance.id}">${fieldValue(bean: itemImportSourceInstance, field: "description")}</g:link></td>
					
						<td>${fieldValue(bean: itemImportSourceInstance, field: "site")}</td>
					
						<td>${fieldValue(bean: itemImportSourceInstance, field: "category")}</td>
					
						<td><g:formatDate date="${itemImportSourceInstance.dateCreated}" /></td>
					
						<td>${fieldValue(bean: itemImportSourceInstance, field: "fileAttachment")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${itemImportSourceInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
