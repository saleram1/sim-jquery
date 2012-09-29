
<%@ page import="com.mercadolibre.apps.sim.data.bo.core.ItemListing" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'itemListing.label', default: 'ItemListing')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="row-fluid">

    <!--
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
                -->
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

                <g:sortableColumn property="gp_id" title="${message(code: 'itemListing.gp_id.label', default: 'Gpid')}" />

                <g:sortableColumn property="mercadoLibreItemId" title="${message(code: 'itemListing.mercadoLibreItemId.label', default: 'Mercado Libre Item Id')}" />

                <g:sortableColumn property="category_id" title="${message(code: 'itemListing.category_id.label', default: 'Category Id')}" />

                <g:sortableColumn property="title" title="${message(code: 'itemListing.title.label', default: 'Title')}" />

                <!--
                <g:sortableColumn property="condition" title="${message(code: 'itemListing.condition.label', default: 'Condition')}" />

                <g:sortableColumn property="currency_id" title="${message(code: 'itemListing.currency_id.label', default: 'Currency Id')}" />
                    -->
                <g:sortableColumn property="price" title="${message(code: 'itemListing.price.label', default: 'Price')}" />

                <g:sortableColumn property="available_quantity" title="${message(code: 'itemListing.available_quantity.label', default: 'Available Quantity')}" />

                <g:sortableColumn property="listing_type_id" title="${message(code: 'itemListing.listing_type_id.label', default: 'Listing Type Id')}" />
                <!--
                <g:sortableColumn property="buying_mode" title="${message(code: 'itemListing.buying_mode.label', default: 'Buying Mode')}" />


                <g:sortableColumn property="acceptsMercadoPago" title="${message(code: 'itemListing.acceptsMercadoPago.label', default: 'Accepts Mercado Pago')}" />

                <g:sortableColumn property="dateCreated" title="${message(code: 'itemListing.dateCreated.label', default: 'Date Created')}" />

                <g:sortableColumn property="lastUpdated" title="${message(code: 'itemListing.lastUpdated.label', default: 'Last Updated')}" />
                -->
                <th></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${itemListingInstanceList}" var="itemListingInstance">
                <tr>

                    <td>${fieldValue(bean: itemListingInstance, field: "gp_id")}</td>

                    <td>${fieldValue(bean: itemListingInstance, field: "mercadoLibreItemId")}</td>

                    <td>${fieldValue(bean: itemListingInstance, field: "category_id")}</td>

                    <td>${fieldValue(bean: itemListingInstance, field: "title")} style="table-layout: fixed;"</td>

                    <!--
                    <td>${fieldValue(bean: itemListingInstance, field: "condition")}</td>

                    <td>${fieldValue(bean: itemListingInstance, field: "currency_id")}</td>
                        -->
                    <td>${fieldValue(bean: itemListingInstance, field: "price")}</td>

                    <td>${fieldValue(bean: itemListingInstance, field: "available_quantity")}</td>

                    <td>${fieldValue(bean: itemListingInstance, field: "listing_type_id")}</td>
                    <!--
                    <td>${fieldValue(bean: itemListingInstance, field: "buying_mode")}</td>


                    <td>${fieldValue(bean: itemListingInstance, field: "acceptsMercadoPago")}</td>

                    <td><g:formatDate date="${itemListingInstance.dateCreated}" /></td>

                    <td><g:formatDate date="${itemListingInstance.lastUpdated}" /></td>
                          -->
                    <td class="link">
                        <g:link action="show" id="${itemListingInstance.id}" class="btn btn-small">Show &raquo;</g:link>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <bootstrap:paginate total="${itemListingInstanceTotal}" />
        </div>
    </div>

</div>
</body>
</html>