<%@ page import="com.mercadolibre.apps.sim.ItemImport" %>



<div class="fieldcontain ${hasErrors(bean: itemImportInstance, field: 'bsfuUUID', 'error')} required">
	<label for="bsfuUUID">
		<g:message code="itemImport.bsfuUUID.label" default="Bsfu UUID" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="bsfuUUID" required="" value="${itemImportInstance?.bsfuUUID}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportInstance, field: 'category', 'error')} ">
	<label for="category">
		<g:message code="itemImport.category.label" default="Category" />
		
	</label>
	<g:textField name="category" value="${itemImportInstance?.category}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="itemImport.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${itemImportInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="itemImport.status.label" default="Status" />
		
	</label>
	<g:select name="status" from="${itemImportInstance.constraints.status.inList}" value="${itemImportInstance?.status}" valueMessagePrefix="itemImport.status" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportInstance, field: 'errorItemsCount', 'error')} required">
	<label for="errorItemsCount">
		<g:message code="itemImport.errorItemsCount.label" default="Error Items Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="errorItemsCount" required="" value="${itemImportInstance.errorItemsCount}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportInstance, field: 'errs', 'error')} ">
	<label for="errs">
		<g:message code="itemImport.errs.label" default="Errs" />
		
	</label>
	<g:select name="errs" from="${com.mercadolibre.apps.sim.ApiError.list()}" multiple="multiple" optionKey="id" size="5" value="${itemImportInstance?.errs*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportInstance, field: 'files', 'error')} ">
	<label for="files">
		<g:message code="itemImport.files.label" default="Files" />
		
	</label>
	<g:select name="files" from="${com.mercadolibre.apps.sim.ItemImportFileSource.list()}" multiple="multiple" optionKey="id" size="5" value="${itemImportInstance?.files*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportInstance, field: 'site', 'error')} ">
	<label for="site">
		<g:message code="itemImport.site.label" default="Site" />
		
	</label>
	<g:textField name="site" value="${itemImportInstance?.site}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportInstance, field: 'validItemsCount', 'error')} required">
	<label for="validItemsCount">
		<g:message code="itemImport.validItemsCount.label" default="Valid Items Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="validItemsCount" required="" value="${itemImportInstance.validItemsCount}"/>
</div>

