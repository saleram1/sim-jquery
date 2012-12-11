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
					<select name="storeCategory" required="required">
						<optgroup label="Mujeres">
							<option value="256">Alpargatas y Ojotas</option>
							<option value="254">Ballerinas</option>
							<option value="259">Borcegos</option>
							<option value="258">Botas y Botinetas</option>
							<option value="257">Botas de Lluvia</option>
							<option value="262">Mocasines y Oxfords</option>
							<option value="249">Sandalias</option>
							<option value="253">Zapatillas</option>
							<option value="250">Zuecos y Plataformas</option>
							<option value="251">Zapatos de Fiesta</option>
							<option value="283">Taco Alto</option>
							<option value="281">Taco Medio</option>
							<option value="280">Taco Bajo</option>
						</optgroup>
						<optgroup label="Hombres">
							<option value="42">Alpargatas</option>
							<option value="321">Botas y Borcegos</option>
							<option value="81">Sandalias y Ojotas</option>
							<option value="41">Zapatillas</option>
							<option value="40">Zapatos</option>
						</optgroup>
						<optgroup label="Niños">
							<option value="73">Zapatillas</option>
							<option value="195">Sandalias y Zuecos</option>
							<option value="72">Zapatos y Ballerinas</option>
							<option value="74">Botas y Borcegos</option>
							<option value="63">Botitas de lluvia</option>
							<option value="75">Alpargatas</option>
						</optgroup>
						<option value="23">Bebe</option>
						<optgroup label="Accesorios">
							<option value="295">Carteras</option>
							<option value="349">Bandoleras y Morrales</option>
							<option value="298">Billeteras Mujeres</option>
							<option value="307">Billeteras Hombres</option>
							<option value="297">Bolsos y Mochilas Mujeres</option>
							<option value="294">Cintos Mujeres</option>
							<option value="305">Cintos Hombres</option>
							<option value="369">Sobres</option>
						</optgroup>
					</select>
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
	<textarea name="htmlDescription" required="required" style="width: 100%; display: block; white-space: pre-wrap;">&lt;div style="width: 500px; margin: 0 auto; text-align: center;"&gt;
	&lt;span&gt;
		&lt;a href="http://www.fotter.com.ar/" target="_blank"&gt;
			&lt;img src="http://www.fotter.com.ar/skin/frontend/fotter/default/images/logo_navidad.png" width="440" /&gt;
		&lt;/a&gt;
	&lt;/span&gt;
	&lt;br /&gt;&lt;br /&gt;
	[$MAGENTO_DESCRIPTION]
	&lt;br /&gt;&lt;br /&gt;
	&lt;span&gt;&amp;copy; 2010-2013 &lt;a href="http://www.fotter.com.ar/" target="_blank"&gt;Fotter&lt;/a&gt; SRL of Argentina&lt;/span&gt;
&lt;/div&gt;</textarea>
	<button id="preview-btn" class="btn" type="button"><i class="icon-search"></i> Preview</button>
</fieldset>
