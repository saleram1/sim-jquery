
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'shoppeUser.label', default: 'ShoppeUser')}" />
		<script type="text/javascript">
			document.addEventListener("DOMContentLoaded", function(e) {
				$('.oauth-verify').click(function(e) {
					var oauthUrl = $('a.oauth-link').attr('href'),
						params = {
							apiBaseURL: $('[name="magentoStoreURI"]').val().trim(),
							apiKey: $('[name="apiKey"]').val().trim(),
							sharedSecret: $('[name="sharedSecret"]').val().trim()
						};
						
					var missing = false;
					for (var k in params) {
						if (params[k].length === 0) {
							missing = true;
							$('[name="' + k + '"]').closest('.control-group').addClass('error');
						}
					}
					if (missing) return;
					
					oauthUrl += '?' + $.param(params);
					window.open(oauthUrl, 'magento-oauth', 'width=650,height=500,menubar=no,location=no,resizable=no,scrollbars=no,status=no');
				});
				
				$('.form-horizontal input[type="text"]').change(function(e) {
					var $el = $(this);
					if ($el.val().trim().length === 0) $el.closest('.control-group').addClass('error');
					else $el.closest('.control-group').removeClass('error');
				});
			});
		</script>
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
  	  			  <button type="button" class="btn">Skip</button>
				  <g:submitButton name="create" class="btn btn-primary save" value="${'Save'}" />
				</div>
			</div>
		</g:form>
	</body>
</html>
