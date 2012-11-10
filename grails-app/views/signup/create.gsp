
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'shoppeUser.label', default: 'ShoppeUser')}" />
		<script type="text/javascript">
		function skipRegistration() {
			alert('Coming soon!');
		}
		</script>
	</head>
	<body>
		<a href="javascript:skipRegistration();" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip registration&hellip;"/></a>
		<div class="nav" role="navigation">	
			<p></p>
		</div>		
		<div id="create-shoppeUser" class="content scaffold-create" role="main">
			<h1>Welcome to MercadoLibre! </h1>                                    <br/>
			<h2>M2M provides further benefits to manage your store inventory using Magento Community or Enterprise and it's free.</h2>

			<g:hasErrors bean="${shoppeUserInstance}">
			<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${shoppeUserInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
			</bootstrap:alert>
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
