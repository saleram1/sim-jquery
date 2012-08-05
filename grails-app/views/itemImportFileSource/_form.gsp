<%@ page import="com.mercadolibre.apps.sim.ItemImportFileSource" %>



<div class="fieldcontain ${hasErrors(bean: itemImportSourceInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="itemImportSource.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${itemImportSourceInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportSourceInstance, field: 'site', 'error')} ">
	<label for="site">
		<g:message code="itemImportSource.site.label" default="Site" />
		
	</label>
	<g:textField name="site" value="${itemImportSourceInstance?.site}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportSourceInstance, field: 'category', 'error')} ">
	<label for="category">
		<g:message code="itemImportSource.category.label" default="Category" />
		
	</label>
	<g:textField name="category" value="${itemImportSourceInstance?.category}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemImportSourceInstance, field: 'fileAttachment', 'error')} required">
	<label for="fileAttachment">
		<g:message code="itemImportSource.fileAttachment.label" default="File Attachment" />
		<span class="required-indicator">*</span>
	</label>
	
</div>
