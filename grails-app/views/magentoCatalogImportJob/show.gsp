<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magentoCatalogImportJob.label', default: 'MagentoCatalogImportJob')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="page-header">
			<h1>Import Job Number <g:fieldValue bean="${magentoCatalogImportJobInstance}" field="id" />  <small>Created on: <g:formatDate date="${magentoCatalogImportJobInstance?.dateCreated}" /></small></h1>
		</div>
		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
		<div class="row-fluid">
			<div class="span4">&nbsp;</div>
			<div class="span4">
				<h3 style="text-align: center;"><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="status"/></h3>
				<div class="progress progress-striped active">
					<div class="bar" style="width: 50%;"></div>
				</div>
				<p class="muted" style="text-align: center;">## out of <g:fieldValue bean="${magentoCatalogImportJobInstance}" field="validItemsCount"/> imported</p>
			</div>
		</div>
		<br />
		<br />
		<div class="row-fluid">
			<div class="span3">&nbsp;</div>
			<div class="span6">
				<table class="table table-condensed table-striped">
					<tbody>
						<tr>
							<td><strong>Category</strong></td>
							<td><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="storeCategory"/></td>
							<td><strong><abbr title="MercadoLibre category code">MELI Category</abbr></strong></td>
							<td><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="meliCategory"/></td>
						</tr>
						<tr>
							<td><strong>Mode</strong></td>
							<td><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="buyingMode"/></td>
							<td><strong><abbr title="Listing Type">Type</abbr></strong></td>
							<td><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="listingTypeId"/></td>
						</tr>
						<g:if test="${magentoCatalogImportJobInstance?.description}">
							<tr>
								<td><strong><g:message code="magentoCatalogImportJob.description.label" default="Description" /></strong></td>
								<td colspan="3"><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="description"/></td>
							</tr>
						</g:if>
						<g:if test="${magentoCatalogImportJobInstance?.colorAppendedToSKU}">
							<tr>
								<td><strong>Color</strong></td>
								<td colspan="3">The attribute <code><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="colorAttributeName" /></code> is appended to SKU</td>
							</tr>
						</g:if>
						<g:if test="${magentoCatalogImportJobInstance?.sizeAppendedToSKU}">
							<tr>
								<td><strong>Size</strong></td>
								<td colspan="3">The attribute <code><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="sizeAttributeName" /></code> is appended to SKU</td>
							</tr>
						</g:if>
					</tbody>
				</table>
			</div>
		</div>
		<!-- <div class="row-fluid">
					<div class="span9">
						<dl>
							<g:if test="${magentoCatalogImportJobInstance?.errorItemsCount}">
								<dt><g:message code="magentoCatalogImportJob.errorItemsCount.label" default="Error Items Count" /></dt>
								<dd><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="errorItemsCount"/></dd>
							</g:if>
						</dl>
					</div>
				</div> -->
	</body>
</html>
