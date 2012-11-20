<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoItemImport" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magentoItemImport.label', default: 'MagentoItemImport')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		
        <g:javascript>
            $(document).ready(function() {
               $('#meliCategory').autocomplete({
                 source: '<g:createLink controller='category' action='search'/>',
				 minLength: 4,
				 maxRows: 10
               });
			   
			   $('#confirm-category').click(function(e) {
				   $('form table').removeClass('hide');
			   });
			   
			   $('[rel="tooltip"]').tooltip();
            });
			
			function _after(response) {
				alert( response.responseText ); // OK!
				var codigos = eval('(' + response.responseText + ')');
				// here I can process the json object ...
			}
		</g:javascript>
		<style>
			.categories-table th, .categories-table td {
				text-align: center !important;
			}
			.categories-table th {
				background: none;
			}
		</style>
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
		<g:formRemote name="startMagentoPull" url="[controller:'magentoItemImport', action:'startImport']" update="updateMe" class="form-horizontal">
			<g:render template="form"/>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.create.label" default="Create" />
				</button>
			</div>
		</g:formRemote>
	</body>
</html>
