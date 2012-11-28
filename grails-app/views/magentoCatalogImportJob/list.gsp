
<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magentoCatalogImportJob.label', default: 'MagentoCatalogImportJob')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">

			<div class="span9">
				
				<div class="page-header">
					<h3>Catalog Imports</h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>
				
				<table class="table table-striped">
					<thead>
						<tr>

                            <g:sortableColumn property="status" title="${message(code: 'magentoCatalogImportJob.buyingMode.label', default: 'Status')}" />

                            <g:sortableColumn property="buyingMode" title="${message(code: 'magentoCatalogImportJob.buyingMode.label', default: 'Buying Mode')}" />

                            <g:sortableColumn property="listingTypeId" title="${message(code: 'magentoCatalogImportJob.listingTypeId.label', default: 'Listing Type Id')}" />

                            <g:sortableColumn property="meliCategory" title="${message(code: 'magentoCatalogImportJob.meliCategory.label', default: 'Meli Category')}" />

                            <g:sortableColumn property="dateCreated" title="${message(code: 'magentoCatalogImportJob.dateCreated.label', default: 'Date Created')}" />
						
							<g:sortableColumn property="description" title="${message(code: 'magentoCatalogImportJob.description.label', default: 'Description')}" />

							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${magentoCatalogImportJobInstanceList}" var="magentoCatalogImportJobInstance">
						<tr>

                            <td>${fieldValue(bean: magentoCatalogImportJobInstance, field: "status")}</td>

                            <td>${fieldValue(bean: magentoCatalogImportJobInstance, field: "buyingMode")}</td>

                            <td>${fieldValue(bean: magentoCatalogImportJobInstance, field: "listingTypeId")}</td>

                            <td>${fieldValue(bean: magentoCatalogImportJobInstance, field: "meliCategory")}</td>

							<td>${fieldValue(bean: magentoCatalogImportJobInstance, field: "dateCreated")}</td>
						
							<td>${fieldValue(bean: magentoCatalogImportJobInstance, field: "description")}</td>
						

							<td class="link">
								<g:link action="show" id="${magentoCatalogImportJobInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${magentoCatalogImportJobInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
