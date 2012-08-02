<%@ page import="com.mercadolibre.apps.sim.ItemImportSource" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'itemImportSource.label', default: 'ItemImportSource')}" />
		<title>New Import</title>
	</head>
	<body>
		<a href="#create-itemImportSource" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-itemImportSource" class="content scaffold-create" role="main">
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>

<!-- scaffolding -->
			<g:hasErrors bean="${itemImportSourceInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${itemImportSourceInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			
			<h1>Upload Items</h1>
			<h1>&nbsp;</h1>
			<bsfu:fileUpload action="upload" controller="image" />

		</div>
	</body>
</html>
