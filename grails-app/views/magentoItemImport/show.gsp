
<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoItemImport" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magentoItemImport.label', default: 'MagentoItemImport')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								<g:message code="default.list.label" args="[entityName]" />
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								<g:message code="default.create.label" args="[entityName]" />
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">

				<div class="page-header">
					<h1><g:message code="default.show.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<dl>
				
					<g:if test="${magentoItemImportInstance?.buyingMode}">
						<dt><g:message code="magentoItemImport.buyingMode.label" default="Buying Mode" /></dt>
						
							<dd><g:fieldValue bean="${magentoItemImportInstance}" field="buyingMode"/></dd>
						
					</g:if>
				
					<g:if test="${magentoItemImportInstance?.listingType}">
						<dt><g:message code="magentoItemImport.listingType.label" default="Listing Type" /></dt>
						
							<dd><g:fieldValue bean="${magentoItemImportInstance}" field="listingType"/></dd>
						
					</g:if>
				
					<g:if test="${magentoItemImportInstance?.meliCategory}">
						<dt><g:message code="magentoItemImport.meliCategory.label" default="Meli Category" /></dt>
						
							<dd><g:fieldValue bean="${magentoItemImportInstance}" field="meliCategory"/></dd>
						
					</g:if>
				
					<g:if test="${magentoItemImportInstance?.productSelection}">
						<dt><g:message code="magentoItemImport.productSelection.label" default="Product Selection" /></dt>
						
							<dd><g:fieldValue bean="${magentoItemImportInstance}" field="productSelection"/></dd>
						
					</g:if>
				
					<g:if test="${magentoItemImportInstance?.storeCategory}">
						<dt><g:message code="magentoItemImport.storeCategory.label" default="Store Category" /></dt>
						
							<dd><g:fieldValue bean="${magentoItemImportInstance}" field="storeCategory"/></dd>
						
					</g:if>
				
					<g:if test="${magentoItemImportInstance?.condition}">
						<dt><g:message code="magentoItemImport.condition.label" default="Condition" /></dt>
						
							<dd><g:fieldValue bean="${magentoItemImportInstance}" field="condition"/></dd>
						
					</g:if>
				
					<g:if test="${magentoItemImportInstance?.currency}">
						<dt><g:message code="magentoItemImport.currency.label" default="Currency" /></dt>
						
							<dd><g:fieldValue bean="${magentoItemImportInstance}" field="currency"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${magentoItemImportInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${magentoItemImportInstance?.id}">
							<i class="icon-pencil"></i>
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>
						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<g:message code="default.button.delete.label" default="Delete" />
						</button>
					</div>
				</g:form>

			</div>

		</div>
	</body>
</html>
