<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob" %>


<div class="fieldcontain ${hasErrors(bean: magentoCatalogImportJobInstance, field: 'status', 'error')} ">
    <label for="status">
        <g:message code="magentoCatalogImportJob.status.label" default="Status" />

    </label>
    <g:select name="status" from="${magentoCatalogImportJobInstance.constraints.status.inList}" value="${magentoCatalogImportJobInstance?.status}" valueMessagePrefix="magentoCatalogImportJob.status" noSelection="['': '']"/>
</div>


<div class="fieldcontain ${hasErrors(bean: magentoCatalogImportJobInstance, field: 'storeCategory', 'error')} required">
    <label for="storeCategory">
        <g:message code="magentoCatalogImportJob.storeCategory.label" default="Store Category" />
        <span class="required-indicator">*</span>
    </label>
    <g:field type="number" name="storeCategory" required="" value="${magentoCatalogImportJobInstance.storeCategory}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: magentoCatalogImportJobInstance, field: 'meliCategory', 'error')} ">
    <label for="meliCategory">
        <g:message code="magentoCatalogImportJob.meliCategory.label" default="Meli Category" />

    </label>
    <g:textField name="meliCategory" value="${magentoCatalogImportJobInstance?.meliCategory}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: magentoCatalogImportJobInstance, field: 'buyingMode', 'error')} ">
	<label for="buyingMode">
		<g:message code="magentoCatalogImportJob.buyingMode.label" default="Buying Mode" />
		
	</label>
	<g:textField name="buyingMode" value="${magentoCatalogImportJobInstance?.buyingMode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: magentoCatalogImportJobInstance, field: 'colorAttributeName', 'error')} ">
	<label for="colorAttributeName">
		<g:message code="magentoCatalogImportJob.colorAttributeName.label" default="Color Attribute Name" />
		
	</label>
	<g:textField name="colorAttributeName" value="${magentoCatalogImportJobInstance?.colorAttributeName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: magentoCatalogImportJobInstance, field: 'sizeAttributeName', 'error')} ">
    <label for="sizeAttributeName">
        <g:message code="magentoCatalogImportJob.sizeAttributeName.label" default="Size Attribute Name" />

    </label>
    <g:textField name="sizeAttributeName" value="${magentoCatalogImportJobInstance?.sizeAttributeName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: magentoCatalogImportJobInstance, field: 'listingTypeId', 'error')} ">
	<label for="listingTypeId">
		<g:message code="magentoCatalogImportJob.listingTypeId.label" default="Listing Type Id" />
		
	</label>
	<g:textField name="listingTypeId" value="${magentoCatalogImportJobInstance?.listingTypeId}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: magentoCatalogImportJobInstance, field: 'errorItemsCount', 'error')} required">
	<label for="errorItemsCount">
		<g:message code="magentoCatalogImportJob.errorItemsCount.label" default="Error Items Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="errorItemsCount" required="" value="${magentoCatalogImportJobInstance.errorItemsCount}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: magentoCatalogImportJobInstance, field: 'validItemsCount', 'error')} required">
	<label for="validItemsCount">
		<g:message code="magentoCatalogImportJob.validItemsCount.label" default="Valid Items Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="validItemsCount" required="" value="${magentoCatalogImportJobInstance.validItemsCount}"/>
</div>

