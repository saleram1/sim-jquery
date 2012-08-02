<%@ page import="com.mercadolibre.apps.sim.ItemImportSource" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'itemImportSource.label', default: 'ItemImportSource')}" />
		<title>New Import</title>

	   	<script type="text/javascript">
			var hook = true;
			window.onbeforeunload = function() {
			    if (hook) {
			        return "Did you save your stuff?"
			    }
			}
			function unhook() {
			    hook = false;
			}
	    </script>
	</head>	
	<body>
		<div id="create-itemImportSource" class="content scaffold-create" role="main">
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>

			<g:hasErrors bean="${itemImportSourceInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${itemImportSourceInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			
			<h1>Upload Items</h1>

			<fieldset>
				<div class="fieldcontain required">
					<label for="site">
						Site
						<span class="required-indicator">*</span>
					</label>

					<select name="site" id="site"><option value="">Selecciona un Sita...</option>
								<option data-id="MLA" value="MLA">Argentina</option>
								<option data-id="MLB" value="MLB">Brasil</option>
								<option data-id="MCO" value="MCO">Colombia</option>
								<option data-id="MCR" value="MCR">Costa Rica</option>
								<option data-id="MEC" value="MEC">Ecuador</option>
								<option data-id="MLC" value="MLC">Chile</option>
								<option data-id="MLM" value="MLM">Mexico</option>
								<option data-id="MLU" value="MLU">Uruguay</option>
								<option data-id="MLV" value="MLV">Venezuela</option>
								<option data-id="MPA" value="MPA">Panamá</option>
								<option data-id="MPE" value="MPE">Perú</option>
								<option data-id="MPT" value="MPT">Portugal</option>
								<option data-id="MRD" value="MRD">Dominicana</option>
					</select>
				</div>

				<div class="fieldcontain required">
					<label for="category">
						Category
						<span class="required-indicator">*</span>
					</label>
					<input type="text" name="category" value="" id="category" />
				</div>

				<div class="fieldcontain">
					<label for="description">
						Description
					</label>
					<input type="text" name="description" value="" id="description" maxLength="80" size="80" />
				</div>

				<div id="dropZone1234" style="background-color:yellow; width: 480; height: 250">
					&nbsp;<br/>
				</div>
			</fieldset>

			<bsfu:fileUpload action="upload" controller="image" dropTarget="dropZone1234" />
		</div>
		
		<script type="text/javascript">
			document.getElementById("description").disabled=true;
		</script>
	</body>
</html>
