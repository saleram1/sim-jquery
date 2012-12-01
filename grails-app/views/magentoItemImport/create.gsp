<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoItemImport" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magentoItemImport.label', default: 'MagentoItemImport')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<style type="text/css">
			.categories-table th, .categories-table td {
				text-align: center !important;
			}
			.categories-table th {
				background: none;
			}
		</style>
		
		<g:javascript>
			function categoryError($input, errorMsg) {
				if (errorMsg === null) $input.siblings('.help-inline').text('').closest('.control-group').removeClass('error');
				else $input.siblings('.help-inline').text(errorMsg).closest('.control-group').addClass('error');
			}
			
			$(document).ready(function() {
				$('#meliCategory').autocomplete({
					maxRows: 10,
					minLength: 4,
					source: '<g:createLink controller="category" action="search" />',
					select: function(e, ui) {
						var match = ui.item.value.match(/^\w{3}\d+/);
						if ($.isArray(match) && match.length) ui.item.value = match[0];
					},
					change: function(e, ui) {
						var $input = $(e.target);
						
						var cat = (ui.item || e.target).value.trim(),
							url = (/^\/sim/.test(document.location.pathname) ? '/sim' : '') + '/categoryConfirm';
						if (/^\w{3}\d+$/.test(cat)) {
							categoryError($input, null);
							$.getJSON(url, {category: cat}, function(data, status, $xhr) {
								var $table = $('form table').addClass('hide');
									
								if (data.error) categoryError($input, data.message);
								else if (data.isFashion === true) $table.removeClass('hide');
							});
						} else {
							categoryError($input, '<g:message code="magentoItemImport.meliCategory.invalidCategory" default="Invalid category code." />');
						}
					}
				});
			   
				$('[rel="tooltip"]').tooltip();
			});
		</g:javascript>
	</head>
	<body>
		<div class="page-header">
			<h1>Create MercadoLibre Listings</h1>
		</div>
		<div>
			<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
			</g:if>
			<g:hasErrors bean="${magentoItemImportInstance}">
				<bootstrap:alert class="alert-error">
					<ul>
						<g:eachError bean="${magentoItemImportInstance}" var="error">
							<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</bootstrap:alert>
			</g:hasErrors>
		</div>
        <g:form class="form-horizontal" action="save" controller="magentoCatalogImportJob" >
            <g:render template="form"/>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-ok icon-white"></i>
                    Next -&gt;
%{--
                    <g:message code="default.button.create.label" default="Save" />
--}%
                </button>
            </div>
        </g:form>
	</body>
</html>
