<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoItemImport" %>
<% attributeNames = ['size', 'accesorio_size', 'shoe_size', 'shoe_size_double', 'marca', 'description', 'name', 'qty', 'price', 'color', 'weight', '_category', '_attribute_set', '_type', '_store'] %>
<fieldset class="fieldset" name="meli">
	<legend>Manage Products</legend>
	<div class="row-fluid">
		<div class="span5">
			<div class="control-group ${hasErrors(bean: magentoItemImportInstance, field: 'storeCategory', 'error')}">
				<label for="storeCategory" class="control-label">
					<g:message code="magentoItemImport.storeCategory.label" default="Store Category" />
				</label>
				<div class="controls">
					<g:textField class="input-small" name="storeCategory" value="${magentoItemImportInstance?.storeCategory}" required="required" />
				</div>
			</div>
		</div>
		<div class="span7">
			<div class="control-group ${hasErrors(bean: magentoItemImportInstance, field: 'meliCategory', 'error')}">
				<label for="meliCategory" class="control-label">
					<g:message code="magentoItemImport.meliCategory.label" default="MLCategory" />
				</label>
				<div class="controls">
					<g:textField id="meliCategory" class="input-small" name="meliCategory" value="${magentoItemImportInstance?.meliCategory}" required="required" />
					<span class="help-inline"></span>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span5">
			<div class="control-group ${hasErrors(bean: magentoItemImportInstance, field: 'stockPercentage', 'error')}">
				<label for="stockPercent" class="control-label">
					<g:message code="magentoItemImport.stockPercentage.label" default="Stock %" />
				</label>
				<div class="controls">
					<div class="input-append">
						<input type="number" min="5" max="100" step="5" class="input-mini" name="stockPercentage" value="${magentoItemImportInstance?.stockPercentage}" required="required" /><span class="add-on" style="height: 15px;">%</span>
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
						<td><g:select name="sizeAttributeName" from="${attributeNames}" value="size" /></td>
						<td><g:checkBox name="sizeAppendedToSKU" /></td>
					</tr>
					<tr>
						<td>Color Primario</td>
						<td><g:select name="colorAttributeName" from="${attributeNames}" value="color" /></td>
						<td><g:checkBox name="colorAppendedToSKU"/></td>
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
</fieldset>
<fieldset class="fieldset" name="meli">
	<legend>Description Template</legend>
	<textarea name="htmlDescription" required="required" style="width: 100%; display: block;">&lt;html&gt;
	&lt;body&gt;
		&lt;div&gt;
			[$MAGENTO_DESCRIPTION]
		&lt;/div&gt; 
	&lt;/body&gt;
&lt;/html&gt;</textarea>
	<button id="preview-btn" class="btn" type="button"><i class="icon-search"></i> Preview</button>
</fieldset>
