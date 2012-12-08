<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magentoCatalogImportJob.label', default: 'MagentoCatalogImportJob')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>

		<style type="text/css">
			#status-container > h3, #status-container > p {
				text-align: center;
			}
			#status-container > .progress {
				margin-bottom: 0;
			}
			#status-container > p:last-child {
				text-align: center; color: #B94A48;
			}
		</style>
		<g:if test="${magentoCatalogImportJobInstance?.status != 'COMPLETE'}">
			<g:javascript>
				$(document).ready(function() {
					var statusUrl = (/^\/sim/.test(document.location.pathname) ? '/sim' : '') + '/magentoCatalogImportJob/status/<g:fieldValue bean="${magentoCatalogImportJobInstance}" field="id" />';

					var $bar = $('#bar'),
						$status = $('#status'),
						$validItems = $('#valid-items'),
						$totalItems = $('#total-items'),
						$errorItems = $('#error-items');

					var updateStatus = setInterval(function() {
						$.getJSON(statusUrl, function(job, status, $xhr) {
							$status.text(job.status);
							$validItems.text(job.validItemsCount);
							$totalItems.text(job.totalItemsCount);

							if (job.errorItemsCount) $errorItems.text(job.errorItemsCount).parent().removeClass('hide');

							var percentComplete = Math.ceil(((job.errorItemsCount + job.validItemsCount) / job.totalItemsCount) * 100);
							$bar.width(percentComplete + '%');

							if (job.status === 'COMPLETE') {
								clearInterval(updateStatus);
								$bar.parent().removeClass('active').addClass('progress-success');
							}
						});
					}, 2000);
				});
			</g:javascript>
		</g:if>
	</head>
	<body>
		<div class="page-header">
			<h1>Import Job Number <g:fieldValue bean="${magentoCatalogImportJobInstance}" field="id" />  <small>Created on: <g:formatDate date="${magentoCatalogImportJobInstance?.dateCreated}" /></small></h1>
		</div>
		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
		<div class="row-fluid">
			<div class="span4">&nbsp;</div>
			<div id="status-container" class="span4">
				<h3 id="status"><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="status" /></h3>
				<% percentComplete = Math.ceil(((magentoCatalogImportJobInstance?.errorItemsCount + magentoCatalogImportJobInstance?.validItemsCount) / magentoCatalogImportJobInstance?.totalItemsCount) * 100) %>
				<div class="progress progress-striped ${percentComplete < 100 ? 'active' : ''}">
					<div id="bar" class="bar" style="width: ${percentComplete}%;"></div>
				</div>
				<p class="muted"><span id="valid-items"><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="validItemsCount" /></span> out of <span id="total-items"><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="totalItemsCount" /></span> imported</p>
				<p class="${magentoCatalogImportJobInstance?.errorItemsCount == 0 ? 'hide' : ''}"><span id="error-items"><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="errorItemsCount" /></span> error(s) found.</p>
			</div>
		</div>
		<br />
		<br />
		<div class="row-fluid">
			<div class="span3">&nbsp;</div>
			<div class="span6">
				<table class="table table-condensed table-striped">
					<tbody>
						<tr>
							<td><strong>Category</strong></td>
							<td><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="storeCategory"/></td>
							<td><strong><abbr title="MercadoLibre category code">MELI Category</abbr></strong></td>
							<td><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="meliCategory"/></td>
						</tr>
						<tr>
							<td><strong>Mode</strong></td>
							<td>Buy It Now</td>
							<td><strong><abbr title="Listing Type">Type</abbr></strong></td>
							<td><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="listingType"/></td>
						</tr>
						<g:if test="${magentoCatalogImportJobInstance?.description}">
							<tr>
								<td><strong><g:message code="magentoCatalogImportJob.description.label" default="Description" /></strong></td>
								<td colspan="3"><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="description"/></td>
							</tr>
						</g:if>
						<g:if test="${magentoCatalogImportJobInstance?.colorAppendedToSKU}">
							<tr>
								<td><strong>Color</strong></td>
								<td colspan="3">The attribute <code><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="colorAttributeName" /></code> is appended to SKU</td>
							</tr>
						</g:if>
						<g:if test="${magentoCatalogImportJobInstance?.sizeAppendedToSKU}">
							<tr>
								<td><strong>Size</strong></td>
								<td colspan="3">The attribute <code><g:fieldValue bean="${magentoCatalogImportJobInstance}" field="sizeAttributeName" /></code> is appended to SKU</td>
							</tr>
						</g:if>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>
