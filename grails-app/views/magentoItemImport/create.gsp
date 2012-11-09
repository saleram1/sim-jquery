<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoItemImport" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magentoItemImport.label', default: 'MagentoItemImport')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">

            <g:javascript>
    function _after(response)
     {
            alert( response.responseText ); // OK!
            var codigos = eval('(' + response.responseText + ')');
            // here I can process the json object ...
     }
</g:javascript>

			<div class="span9">

				<div class="page-header">
					<h1>Create MercadoLibre Listings</h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

                <div id="updateMe"></div>


				<g:hasErrors bean="${magentoItemImportInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${magentoItemImportInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>

				<fieldset>
					<g:formRemote name="startMagentoPull" url="[controller:'magentoItemImport', action:'startImport']" update="updateMe" onSuccess="_after(e)" class="form-horizontal">
                        <fieldset class="form">
                            <g:render template="form"/>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									<g:message code="default.button.create.label" default="Create" />
								</button>
							</div>
						</fieldset>
					</g:formRemote>
				</fieldset>
				
			</div>

		</div>
	</body>
</html>
