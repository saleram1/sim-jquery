<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoItemImport" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magentoItemImport.label', default: 'MagentoItemImport')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<style type="text/css">
			.categories-table th, .categories-table td {
				text-align: center !important;
			}
			.categories-table th {
				background: none;
			}
			textarea {
				resize: none;
				height: 300px !important;
				margin-bottom: 15px !important;
				font-family: Monaco, Menlo, Consolas, 'Courier New', monospace !important;
			}
			.align-right {
				text-align: right;
			}
		</style>
		
		<g:javascript>
			function categoryError($input, errorMsg) {
				if (errorMsg === null) $input.siblings('.help-inline').text('').closest('.control-group').removeClass('error');
				else $input.siblings('.help-inline').text(errorMsg).closest('.control-group').addClass('error');
			}
			
			$(document).ready(function() {
				$('#meliCategory').autocomplete({
					maxRows: 10,
					minLength: 4,
					source: '<g:createLink controller="category" action="search" />',
					select: function(e, ui) {
						var match = ui.item.value.match(/^\w{3}\d+/);
						if ($.isArray(match) && match.length) ui.item.value = match[0];
					},
					change: function(e, ui) {
						var $input = $(e.target);
						
						var cat = (ui.item || e.target).value.trim(),
							url = (/^\/sim/.test(document.location.pathname) ? '/sim' : '') + '/categoryConfirm';
						if (/^\w{3}\d+$/.test(cat)) {
							categoryError($input, null);
							$.getJSON(url, {category: cat}, function(data, status, $xhr) {
								var $table = $('form table').addClass('hide');
									
								if (data.error) categoryError($input, data.message);
								else if (data.isFashion === true) $table.removeClass('hide');
							});
						} else {
							categoryError($input, '<g:message code="magentoItemImport.meliCategory.invalidCategory" default="Invalid category code." />');
						}
					}
				});

				$('#preview-btn').click(function(e) {
					var htmlTemplate = $('textarea').val().replace('[[MAGENTO_DESCRIPTION]]', '<img src="http://placehold.it/500x400&amp;text=Magento+product+description+goes+here." />');
					htmlTemplate += '<a href="#" onclick="window.close();" style="position: fixed; bottom: 0; right: 0;">Close Preview</a>';
					
					window.open('data:text/html,' + htmlTemplate, null, 'height=600,width=800,status=no,menubar=no,location=no,toolbar=no');
					e.preventDefault();
				});

				$('form').submit(function(e, submitForm) {
					if (submitForm !== true) {
						var form = this,
							url = (/^\/sim/.test(document.location.pathname) ? '/sim' : '') + '/magentoCatalogImportJob/duplicates',
							xhrData = {
								storeCategory: $('input[name="storeCategory"]', this).val().trim(),
								meliCategory: $('input[name="meliCategory"]', this).val().trim()
							};

						$.getJSON(url, xhrData, function(data, status, $xhr) {
							if (data.isDuplicate === true) alert('Duplicate import job!');
							else $(form).trigger('submit', [true]);
						});
						e.preventDefault();
					}
				});
			   
				$('[rel="tooltip"]').tooltip();
			});
		</g:javascript>
	</head>
	<body>
		<div class="page-header">
			<h1>Create MercadoLibre Listings</h1>
		</div>
		<div>
			<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
			</g:if>
			<g:hasErrors bean="${magentoItemImportInstance}">
				<bootstrap:alert class="alert-error">
					<ul>
						<g:eachError bean="${magentoItemImportInstance}" var="error">
							<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</bootstrap:alert>
			</g:hasErrors>
		</div>
		<g:form class="form-horizontal" action="save" controller="magentoCatalogImportJob">
			<g:render template="form" />
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-download-alt icon-white"></i> <g:message code="default.button.startImport.label" default="Start Import" />
				</button>
			</div>
		</g:form>
	</body>
</html>
