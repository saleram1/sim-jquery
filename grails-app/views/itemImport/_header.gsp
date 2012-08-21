	<header class="jumbotron subhead" id="almost">
		<h1>Catalog Upload - Status</h1>
		<g:if test="${'COMPLETED' == itemImportInstance.status}">
			<form action="javascript:alert('window.close will now be called..');"
			<button class="btn btn-large" type="submit" id="_action_complete">
				<i class="icon-check "></i>
				<g:message code="cmplmngr" default="Complete" />
			</button>
			<form>
		</g:if>
		<g:else>
			<h2>&hellip;</h2>
		</g:else>
	</header>
