
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'shoppeUser.label', default: 'ShoppeUser')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-shoppeUser" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip registration&hellip;"/></a>
		<div class="nav" role="navigation">	
			<p></p>
		</div>

		<g:if test="${flash.message}">
		<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>

		<g:hasErrors bean="${shoppeUserInstance}">
		<bootstrap:alert class="alert-error">
		<ul>
			<g:eachError bean="${shoppeUserInstance}" var="error">
			<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
			</g:eachError>
		</ul>
		</bootstrap:alert>
		</g:hasErrors>
		
		<div id="create-shoppeUser" class="content scaffold-create" role="main">
			<h1>Get started – it’s free.</h1>
			<h2>Registration provides further benefits to manage your Shoppe.</h2>

			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${shoppeUserInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${shoppeUserInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" useToken="true">
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${'Save'}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
