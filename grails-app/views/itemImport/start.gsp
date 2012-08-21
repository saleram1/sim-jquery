<%@ page import="com.mercadolibre.apps.sim.ItemImport" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'itemImport.label', default: 'ItemImport')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<script type="text/javascript">
		function disableStartButton(id) {
		    document.getElementById("_action_start").disabled = true;
		    document.getElementById("starter-form").style.display = 'none';
		    setTimeout(function() {
		        window.location.href = "${createLink(controller: 'itemImport', action: 'show', absolute: true)}" + '/' + id;
		    },
		    2000);
		}
		</script>
	</head>
	<body>	
		<div class="row-fluid">

			<div class="span11">

			<header class="jumbotron subhead" id="almost">
			  <h1>Catalog Upload</h1>
			  <p class="lead">Confirm these files are correct and click Continue.</p>
			</header>

			<bootstrap:alert class="alert-info">
				<!-- Dynamic timestamp after the Start is engaged -->
				<div id="uploadStartTime"></div>
			</bootstrap:alert>

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

			<div class="form-actions" id="starter-form">
				<g:formRemote name="myForm" on404="alert('Action not found!')" after="disableStartButton(${itemImportInstance?.id})"
				 	update="uploadStartTime" url="[controller: 'itemImport', action:'startUpload']">

						<g:hiddenField name="id" value="${itemImportInstance?.id}" />
						<g:hiddenField name="bsfuUUID" value="${itemImportInstance?.bsfuUUID}" />

						<button class="btn" type="reset" id="_action_cancel">
							<i class="icon-arrow-left"></i>
							<g:message code="default.button.cancel.label" default="Cancel" />
						</button>
						<button class="btn btn-primary" type="submit" id="_action_start">
							<i class="icon-upload icon-white"></i>
							<g:message code="default.button.ok.label" default="Continue" />
						</button>
					</div>
				</g:formRemote>
			</div>
		</div>

		<script>
		    $(document).ready(function() {
		        $('#_action_cancel').click(function() {
					window.location.href = "${createLink(controller: 'itemImportFileSource', action: 'create', absolute: true)}"
		        });
		    });
		</script>
	</body>
</html>