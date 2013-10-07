<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.ItemImportFileSource" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'itemImportSource.label', default: 'ItemImportSource')}" />
		<title>M2M</title>
	</head>
	<body>
		<header class="jumbotron subhead" id="overview">
		  <h1>Items Uploader</h1>
		  <p class="lead">Choose a column for SKU, enter a Description in the fields below and click 'Add Files&hellip;'<br/>
		 	make sure your inventory files end in &quot;.csv&quot;.</p>
		</header>

		<div class="container section">		
		  <div class="span9">
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>

			<g:hasErrors bean="${itemImportSourceInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${itemImportSourceInstance}" var="error">
				<li><g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<fieldset>
				<div class="fieldcontain required">
					<label for="keyColumn">
						SKU Column
						<span class="required-indicator">*</span>
					</label>
					<g:select name="keyColumn" id="keyColumn" from="${'A'..'L'}" />
				</div>

				<div class="fieldcontain">
					<label for="description">
						Description
					</label>
					<input type="text" name="description" value="" id="description" size="80" />
				</div>
			</fieldset>

			<div id="update4">&nbsp;</div>

			<bsfu:fileUpload action="upload" controller="itemImportFileSource" action="save" maxNumberOfFiles="${15}" maxFileSize="${250*1024}" formData="${formDataModelMap}" acceptFileTypes="/(\\.|\\/)(jpeg|jpg|png|gif)\$/i" dropTarget="dropZone51" />
			
			<div id="dropZone51" dropzone="copy"><p>Drag-n-Drop</p> </div>
		  </div>
		</div>

		<script type="text/javascript">
			var hook = true;
			window.onbeforeunload = function() {
			    if (hook) {
			        return "Did you save your files to the server?"
			    }
			}
			function unhook() {
			    hook = false;
			}
		</script>
		
		<script type="text/javascript">
		$('#fileupload')
		    .bind('fileuploaddone', function (e, data) { alert('Your upload is complete!'); unhook();  window.location.href = "${createLink(controller: 'itemImport', action: 'showUploadProgress', absolute: true)}"; return false; })
		</script>
	</body>
</html>