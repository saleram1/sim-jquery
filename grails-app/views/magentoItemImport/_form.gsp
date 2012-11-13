<%@ page import="com.mercadolibre.apps.sim.data.bo.imports.MagentoItemImport" %>


<fieldset class="fieldset" name="meli">
    <legend>Manage Products</legend>

    <div class="fieldcontain ${hasErrors(bean: magentoItemImportInstance, field: 'productSelection', 'error')} ">
        <label for="productSelection">
            <g:message code="magentoItemImport.productSelection.label" default="Store Selector" />
        </label>
        <g:select name="productSelection" from="${magentoItemImportInstance.constraints.productSelection.inList}" value="${magentoItemImportInstance?.productSelection}" valueMessagePrefix="magentoItemImport.productSelection" noSelection="['': '']"/>
    </div>


    <div class="fieldcontain ${hasErrors(bean: magentoItemImportInstance, field: 'storeCategory', 'error')} ">
        <label for="storeCategory">
            <g:message code="magentoItemImport.storeCategory.label" default="Store Category" />
        </label>
        <g:textField class="input-small" name="storeCategory" value="${magentoItemImportInstance?.storeCategory}"/>


        <label for="meliCategory">
            <g:message code="magentoItemImport.meliCategory.label" default="MLCategory" />
        </label>
        <g:textField id="meliCategory" class="input-small" name="meliCategory" value="${magentoItemImportInstance?.meliCategory}"/>
    </div>

</fieldset>


<fieldset class="fieldset" name="meli">
    <legend>ML Marketplace</legend>
    <div class="fieldcontain ${hasErrors(bean: magentoItemImportInstance, field: 'listingType', 'error')} ">
        <label for="listingType">
            <g:message code="magentoItemImport.listingType.label" default="Listing Type" />
        </label>
        <g:select name="listingType" from="${magentoItemImportInstance.constraints.listingType.inList}" value="${magentoItemImportInstance?.listingType}" valueMessagePrefix="magentoItemImport.listingType" noSelection="['': '']"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: magentoItemImportInstance, field: 'buyingMode', 'error')} ">
        <label for="buyingMode">
            <g:message code="magentoItemImport.buyingMode.label" default="Mode" />
        </label>
        <g:select name="buyingMode" from="${magentoItemImportInstance.constraints.buyingMode.inList}" value="${magentoItemImportInstance?.buyingMode}" valueMessagePrefix="magentoItemImport.buyingMode" noSelection="['': '']"/>
    </div>

    <div>
        <label for="updateMe"></label>
        <div id="updateMe">&nbsp;</div>
    </div>

</fieldset>
