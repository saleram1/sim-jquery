<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'shoppeUser.label', default: 'ShoppeUser')}" />
		<g:javascript>
			$(document).ready(function(e) {
				$('.verify-api').click(function(e) {
					$(this).addClass('btn-success').attr('disabled', 'disabled').html('<i class="icon-ok icon-white"></i>');
				});
				
				$('.form-horizontal input[type="text"]').change(function(e) {
					var $el = $(this);
					if ($el.val().trim().length === 0) $el.closest('.control-group').addClass('error');
					else $el.closest('.control-group').removeClass('error');
				});
			});
		</g:javascript>
	</head>
	<body>
		<div class="page-header">
			<h1>Welcome to MercadoLibre!</h1>
		</div>
		<p class="lead">M2M provides further benefits to manage your store inventory using Magento Community or Enterprise and it's free.</p>
		
		<g:hasErrors bean="${shoppeUserInstance}">
			<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${shoppeUserInstance}" var="error">
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
			</bootstrap:alert>
		</g:hasErrors>
		
		<g:form action="save" useToken="true" class="form-horizontal">
			<g:render template="form"/>
			<div class="row-fluid">
				<div class="form-actions">
					<g:link controller="magentoItemImport" action="create" class="btn">Skip</g:link>
					<g:submitButton name="create" class="btn btn-primary save" value="${'Save'}" />
				</div>
			</div>
		</g:form>
	</body>
</html>