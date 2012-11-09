
<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoItemImport" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'magentoItemImport.label', default: 'MagentoItemImport')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li class="active">
							<g:link class="list" action="list">
								<i class="icon-list icon-white"></i>
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
					<h1><g:message code="default.list.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>
				
				<table class="table table-striped">
					<thead>
						<tr>
						
							<g:sortableColumn property="buyingMode" title="${message(code: 'magentoItemImport.buyingMode.label', default: 'Buying Mode')}" />
						
							<g:sortableColumn property="listingType" title="${message(code: 'magentoItemImport.listingType.label', default: 'Listing Type')}" />
						
							<g:sortableColumn property="meliCategory" title="${message(code: 'magentoItemImport.meliCategory.label', default: 'Meli Category')}" />
						
							<g:sortableColumn property="productSelection" title="${message(code: 'magentoItemImport.productSelection.label', default: 'Product Selection')}" />
						
							<g:sortableColumn property="storeCategory" title="${message(code: 'magentoItemImport.storeCategory.label', default: 'Store Category')}" />
						
							<g:sortableColumn property="condition" title="${message(code: 'magentoItemImport.condition.label', default: 'Condition')}" />
						
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${magentoItemImportInstanceList}" var="magentoItemImportInstance">
						<tr>
						
							<td>${fieldValue(bean: magentoItemImportInstance, field: "buyingMode")}</td>
						
							<td>${fieldValue(bean: magentoItemImportInstance, field: "listingType")}</td>
						
							<td>${fieldValue(bean: magentoItemImportInstance, field: "meliCategory")}</td>
						
							<td>${fieldValue(bean: magentoItemImportInstance, field: "productSelection")}</td>
						
							<td>${fieldValue(bean: magentoItemImportInstance, field: "storeCategory")}</td>
						
							<td>${fieldValue(bean: magentoItemImportInstance, field: "condition")}</td>
						
							<td class="link">
								<g:link action="show" id="${magentoItemImportInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${magentoItemImportInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
