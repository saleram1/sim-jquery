
<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magentoCatalogImportJob.label', default: 'MagentoCatalogImportJob')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">

			<div class="span9">

				<div class="page-header">
					<h3>Catalog Import Job</h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<dl>

                    <g:if test="${magentoCatalogImportJobInstance?.status}">
                        <dt><g:message code="magentoCatalogImportJob.status.label" default="Status" /></dt>

                        <dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="status"/></dd>

                    </g:if>
				
					<g:if test="${magentoCatalogImportJobInstance?.buyingMode}">
						<dt><g:message code="magentoCatalogImportJob.buyingMode.label" default="Buying Mode" /></dt>
						
							<dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="buyingMode"/></dd>
						
					</g:if>
				
					<g:if test="${magentoCatalogImportJobInstance?.colorAppendedToSKU}">
						<dt><g:message code="magentoCatalogImportJob.colorAppendedToSKU.label" default="Color Appended To SKU" /></dt>
						
							<dd><g:formatBoolean boolean="${magentoCatalogImportJobInstance?.colorAppendedToSKU}" /></dd>
						
					</g:if>
				
					<g:if test="${magentoCatalogImportJobInstance?.colorAttributeName}">
						<dt><g:message code="magentoCatalogImportJob.colorAttributeName.label" default="Color Attribute Name" /></dt>
						
							<dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="colorAttributeName"/></dd>
						
					</g:if>
				
					<g:if test="${magentoCatalogImportJobInstance?.description}">
						<dt><g:message code="magentoCatalogImportJob.description.label" default="Description" /></dt>
						
							<dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="description"/></dd>
						
					</g:if>
				
					<g:if test="${magentoCatalogImportJobInstance?.listingTypeId}">
						<dt><g:message code="magentoCatalogImportJob.listingTypeId.label" default="Listing Type Id" /></dt>
						
							<dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="listingTypeId"/></dd>
						
					</g:if>

                    <g:if test="${magentoCatalogImportJobInstance?.storeCategory}">
                        <dt><g:message code="magentoCatalogImportJob.storeCategory.label" default="Store Category" /></dt>

                        <dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="storeCategory"/></dd>

                    </g:if>

                    <g:if test="${magentoCatalogImportJobInstance?.meliCategory}">
						<dt><g:message code="magentoCatalogImportJob.meliCategory.label" default="Meli Category" /></dt>
						
							<dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="meliCategory"/></dd>
						
					</g:if>

                    <g:if test="${magentoCatalogImportJobInstance?.productSelection}">
						<dt><g:message code="magentoCatalogImportJob.productSelection.label" default="Product Selection" /></dt>
						
							<dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="productSelection"/></dd>
						
					</g:if>
				
					<g:if test="${magentoCatalogImportJobInstance?.sizeAppendedToSKU}">
						<dt><g:message code="magentoCatalogImportJob.sizeAppendedToSKU.label" default="Size Appended To SKU" /></dt>
						
							<dd><g:formatBoolean boolean="${magentoCatalogImportJobInstance?.sizeAppendedToSKU}" /></dd>
						
					</g:if>
				
					<g:if test="${magentoCatalogImportJobInstance?.sizeAttributeName}">
						<dt><g:message code="magentoCatalogImportJob.sizeAttributeName.label" default="Size Attribute Name" /></dt>
						
							<dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="sizeAttributeName"/></dd>
						
					</g:if>


					<g:if test="${magentoCatalogImportJobInstance?.dateCreated}">
						<dt><g:message code="magentoCatalogImportJob.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${magentoCatalogImportJobInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${magentoCatalogImportJobInstance?.errorItemsCount}">
						<dt><g:message code="magentoCatalogImportJob.errorItemsCount.label" default="Error Items Count" /></dt>
						
							<dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="errorItemsCount"/></dd>
						
					</g:if>

					<g:if test="${magentoCatalogImportJobInstance?.validItemsCount}">
						<dt><g:message code="magentoCatalogImportJob.validItemsCount.label" default="Valid Items Count" /></dt>
						
							<dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="validItemsCount"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${magentoCatalogImportJobInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${magentoCatalogImportJobInstance?.id}">
							<i class="icon-pencil"></i>
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>

					</div>
				</g:form>

			</div>

		</div>
	</body>
</html>
