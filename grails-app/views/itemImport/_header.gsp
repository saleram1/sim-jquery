
	<header class="jumbotron subhead" id="almost">
		<h1>Upload Status</h1>
		<g:if test="${'COMPLETED' == itemImportInstance.status}">
			<form action="javascript:alert('window.close will now be called..');"
			<button class="btn btn-large" type="submit" name="_action_cancel">
				<i class="icon-check "></i>
				<g:message code="xxx" default="Complete" />
			</button>
			<form>
		</g:if>
		<g:else>
			<h2>&hellip;</h2>
		</g:else>
	</header>
