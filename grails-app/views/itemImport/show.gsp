
<%@ page import="com.mercadolibre.apps.sim.ItemImport" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<meta http-equiv="refresh" content="15">
		<g:set var="entityName" value="${message(code: 'itemImport.label', default: 'ItemImport')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>

	<body>
		<a href="javascript:history.go(-1);" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

		<div id="show-itemImport" class="content scaffold-show" role="main">
			<header class="jumbotron subhead" id="overview">
			  <h1>ItemImport View</h1>
			</header>
					
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>

			<ol class="property-list itemImport">
				<g:hiddenField name="bsfuUUID" value="${itemImportInstance?.bsfuUUID}"/>
				
			<g:if test="${itemImportInstance?.site}">
			<li class="fieldcontain">
				<span id="site-label" class="property-label"><g:message code="itemImport.site.label" default="Site" /></span>
				
					<span class="property-value" aria-labelledby="site-label"><g:fieldValue bean="${itemImportInstance}" field="site"/></span>
				
			</li>
			</g:if>
						
				<g:if test="${itemImportInstance?.category}">
				<li class="fieldcontain">
					<span id="category-label" class="property-label"><g:message code="itemImport.category.label" default="Category" /></span>
					
						<span class="property-value" aria-labelledby="category-label"><g:fieldValue bean="${itemImportInstance}" field="category"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${itemImportInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="itemImport.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${itemImportInstance}" field="description"/></span>
					
				</li>
				</g:if>

		
			<g:if test="${itemImportInstance?.validItemsCount}">
			<li class="fieldcontain">
				<span id="validItemsCount-label" class="property-label"><g:message code="itemImport.validItemsCount.label" default="Valid Items Count" /></span>
				
					<span class="property-value" aria-labelledby="validItemsCount-label"><g:fieldValue bean="${itemImportInstance}" field="validItemsCount"/></span>
				
			</li>
			</g:if>
		

				<g:if test="${itemImportInstance?.errorItemsCount}">
				<li class="fieldcontain">
					<span id="errorItemsCount-label" class="property-label"><g:message code="itemImport.errorItemsCount.label" default="Error Items Count" /></span>
					
						<span class="property-value" aria-labelledby="errorItemsCount-label"><g:fieldValue bean="${itemImportInstance}" field="errorItemsCount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${itemImportInstance?.errs}">
				<li class="fieldcontain">
					<span id="errs-label" class="property-label"><g:message code="itemImport.errs.label" default="Errs" /></span>
					
						<g:each in="${itemImportInstance.errs}" var="e">
						<span class="property-value" aria-labelledby="errs-label"><g:link controller="apiError" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${itemImportInstance?.files}">
				<li class="fieldcontain">
					<span id="files-label" class="property-label"><g:message code="itemImport.files.label" default="Files" /></span>
					
						<g:each in="${itemImportInstance.files}" var="f">
						<span class="property-value" aria-labelledby="files-label"><g:link controller="itemImportFileSource" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>

			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${itemImportInstance?.id}" />
					<g:link class="edit" action="edit" id="${itemImportInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
