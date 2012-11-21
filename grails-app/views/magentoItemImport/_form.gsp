<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoItemImport" %>
<% attributeNames = ['size', 'marca', 'description', 'name', 'qty', 'price', 'color', 'weight', '_category', '_attribute_set', '_type', '_store'] %>
<fieldset class="fieldset" name="meli">
	<legend>Manage Products</legend>
	<div class="control-group ${hasErrors(bean: magentoItemImportInstance, field: 'productSelection', 'error')}">
		<label for="productSelection" class="control-label">
			<g:message code="magentoItemImport.productSelection.label" default="Store Selector" />
		</label>
		<div class="controls">
			<g:select name="productSelection" from="${magentoItemImportInstance.constraints.productSelection.inList}" value="${magentoItemImportInstance?.productSelection}" valueMessagePrefix="magentoItemImport.productSelection" noSelection="['': '']" />
		</div>
	</div>
	<div class="row-fluid">
		<div class="span5">
			<div class="control-group ${hasErrors(bean: magentoItemImportInstance, field: 'storeCategory', 'error')}">
				<label for="storeCategory" class="control-label">
					<g:message code="magentoItemImport.storeCategory.label" default="Store Category" />
				</label>
				<div class="controls">
					<g:textField class="input-small" name="storeCategory" value="${magentoItemImportInstance?.storeCategory}" />
				</div>
			</div>
		</div>
		<div class="span7">
			<div class="control-group ${hasErrors(bean: magentoItemImportInstance, field: 'meliCategory', 'error')}">
				<label for="meliCategory" class="control-label">
					<g:message code="magentoItemImport.meliCategory.label" default="MLCategory" />
				</label>
				<div class="controls">
					<div class="input-append">
						<g:textField id="meliCategory" class="input-small" name="meliCategory" value="${magentoItemImportInstance?.meliCategory}" /><button id="confirm-category" type="button" class="btn btn-small" style="padding: 4px 9px 3px;">Confirm</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span10 offset1">
			<table class="categories-table table table-striped hide">
				<thead>
					<tr>
						<th>Name</th>
						<th>Value</th>
						<th rel="tooltip" title="Check this if the attribute is &lt;br/&gt; appended to the product SKU">In SKU</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Talle</td>
						<td>
							<g:select name="sizeAttributeName" from="${attributeNames}" value="size" />
						</td>
						<td>
							<g:checkBox name="sizeAppendedToSKU" />
						</td>
					</tr>
					<tr>
						<td>Color Primario</td>
						<td>
							<g:select name="colorAttributeName" from="${attributeNames}" value="color" />
						</td>
						<td>
							<g:checkBox name="colorAppendedToSKU"/>
						</td>
					</tr>
			</table>
		</div>
	</div>
</fieldset>
<fieldset class="fieldset" name="meli">
	<legend>ML Marketplace</legend>
	<div class="control-group ${hasErrors(bean: magentoItemImportInstance, field: 'listingType', 'error')}">
		<label for="listingType" class="control-label">
			<g:message code="magentoItemImport.listingType.label" default="Listing Type" />
		</label>
		<div class="controls">
			<g:select name="listingType" from="${magentoItemImportInstance.constraints.listingType.inList}" value="${magentoItemImportInstance?.listingType}" valueMessagePrefix="magentoItemImport.listingType" noSelection="['': '']"/>
		</div>
	</div>
	<div class="control-group ${hasErrors(bean: magentoItemImportInstance, field: 'buyingMode', 'error')}">
		<label for="buyingMode" class="control-label">
			<g:message code="magentoItemImport.buyingMode.label" default="Mode" />
		</label>
		<div class="controls">
			<g:select name="buyingMode" from="${magentoItemImportInstance.constraints.buyingMode.inList}" value="${magentoItemImportInstance?.buyingMode}" valueMessagePrefix="magentoItemImport.buyingMode" noSelection="['': '']"/>
		</div>
	</div>
</fieldset>