
<%@ page import="com.mercadolibre.apps.sim.ItemImportFileSource" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'itemImportSource.label', default: 'ItemImportSource')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-itemImportSource" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-itemImportSource" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list itemImportSource">
			
				<g:if test="${itemImportSourceInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="itemImportSource.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${itemImportSourceInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${itemImportSourceInstance?.site}">
				<li class="fieldcontain">
					<span id="site-label" class="property-label"><g:message code="itemImportSource.site.label" default="Site" /></span>
					
						<span class="property-value" aria-labelledby="site-label"><g:fieldValue bean="${itemImportSourceInstance}" field="site"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${itemImportSourceInstance?.category}">
				<li class="fieldcontain">
					<span id="category-label" class="property-label"><g:message code="itemImportSource.category.label" default="Category" /></span>
					
						<span class="property-value" aria-labelledby="category-label"><g:fieldValue bean="${itemImportSourceInstance}" field="category"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${itemImportSourceInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="itemImportSource.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${itemImportSourceInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${itemImportSourceInstance?.fileAttachment}">
				<li class="fieldcontain">
					<span id="fileAttachment-label" class="property-label"><g:message code="itemImportSource.fileAttachment.label" default="File Attachment" /></span>
					
						<span class="property-value" aria-labelledby="fileAttachment-label"><g:fieldValue bean="${itemImportSourceInstance}" field="fileAttachment"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${itemImportSourceInstance?.id}" />
					<g:link class="edit" action="edit" id="${itemImportSourceInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
