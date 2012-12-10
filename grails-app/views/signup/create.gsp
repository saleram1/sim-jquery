<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'shoppeUser.label', default: 'ShoppeUser')}" />
		<g:javascript>
			var baseUrl = /^\/sim/.test(document.location.pathname) ? '/sim' : '';
			
			$(document).ready(function(e) {
				var $verifyBtn = $('.verify-api'),
					$submitBtn = $('form input[type="submit"]'),
					$inputs = $('.form-horizontal input[type="text"]');
					
				function checkForm() {
					var fieldsOk = $verifyBtn.hasClass('btn-success');
					
					$inputs.each(function(i, el) {
						if (el.value.trim().length === 0) fieldsOk = false;
					});
					
					if (fieldsOk) $submitBtn.removeAttr('disabled');
					else $submitBtn.attr('disabled', 'disabled');
				}
				
				function checkVerifyFieldset() {
					if (!$verifyBtn.hasClass('btn-success')) {
						var fieldsOk = true;
						$inputs.filter('fieldset.magento-api input').each(function(i, el) {
							if (el.value.trim().length === 0) fieldsOk = false;
						});
					
						if (fieldsOk) $verifyBtn.removeAttr('disabled');
						else $verifyBtn.attr('disabled', 'disabled');
					}
				}
				checkVerifyFieldset();
				
				$inputs.change(function(e) {
					var $el = $(this);
					if ($el.is('fieldset.magento-api input')) {
						$verifyBtn.removeClass('btn-success').text('Verify').siblings('.help-inline').text('');
					}
					
					if ($el.val().trim().length === 0) $el.closest('.control-group').addClass('error');
					else $el.closest('.control-group').removeClass('error');
					
					checkVerifyFieldset();
					checkForm();
				});
				
				$verifyBtn.click(function(e) {
					var params = {},
						url = baseUrl + '/verify';
					
					$inputs.each(function(i, el) {
						if ($(el).is('fieldset.magento-api input')) params[el.name] = el.value;
					});
					
					$verifyBtn.attr('disabled', 'disabled').html('<img src="' + baseUrl + '/images/spinner.gif" style="height: 14px;" />').siblings('.help-inline').text('').closest('.control-group').removeClass('error');
					
					$.getJSON(url, params, function(data, status, $xhr) {
						if (data.status === 'OK') {
							$verifyBtn.addClass('btn-success').html('<i class="icon-ok icon-white"></i>');
							checkForm();
						} else {
							$verifyBtn.removeAttr('disabled').text('Verify').siblings('.help-inline').text(data.message);
						}
					});
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
					<g:submitButton name="create" class="btn btn-primary save" value="${'Save'}" disabled="disabled" />
				</div>
			</div>
		</g:form>
	</body>
</html>