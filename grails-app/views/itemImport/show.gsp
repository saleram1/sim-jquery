<%@ page import="com.mercadolibre.apps.sim.ItemImport" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<meta http-equiv="refresh" content="10" />
		<g:set var="entityName" value="${message(code: 'itemImport.label', default: 'ItemImport')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:if test="${session.ml_access_token}">
		
		<div class="row-fluid">
			<div class="span9">

			<div id="makeMeAnUpdateICannotRefuse">
				<g:render template="header" model="['itemImportInstance':itemImportInstance]" />
			</div>
			
			<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
			</g:if>

			<dl>							
				<g:if test="${itemImportInstance?.category}">
					<dt><g:message code="itemImport.category.label" default="Category" /></dt>
					
						<dd><g:fieldValue bean="${itemImportInstance}" field="category"/></dd>
					
				</g:if>
			
				<g:if test="${itemImportInstance?.description}">
					<dt><g:message code="itemImport.description.label" default="Description" /></dt>
					
						<dd><g:fieldValue bean="${itemImportInstance}" field="description"/></dd>
					
				</g:if>

				<dt><dd></dd></dt>
				
				<g:if test="${itemImportInstance?.validItemsCount}">
					<dt><g:message code="itemImport.validItemsCount.label" default="Valid Items" /></dt>
					
						<dd><g:fieldValue bean="${itemImportInstance}" field="validItemsCount"/></dd>
						
				</g:if>
				
				<g:if test="${itemImportInstance?.errorItemsCount}">
					<dt><g:message code="itemImport.errorItemsCount.label" default="Error Items Count" /></dt>

						<dd><g:fieldValue bean="${itemImportInstance}" field="errorItemsCount"/></dd>
				</g:if>

				<g:if test="${itemImportInstance?.files}">
					<dt><g:message code="itemImport.files.label" default="Files" /></dt>

						<g:each in="${itemImportInstance.files}" var="f">
							<dd><g:link controller="itemImportFileSource" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></dd>
						</g:each>
				</g:if>

				<g:if test="${itemImportInstance?.errs}">
					<dt><g:message code="itemImport.errs.label" default="Errs" /></dt>
						<g:each in="${itemImportInstance.errs}" var="e">
						<dd><g:link controller="apiError" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></dd>
						</g:each>
				</g:if>
			</dl>
		</div>
	</div>
	</g:if>
</body>
</html>