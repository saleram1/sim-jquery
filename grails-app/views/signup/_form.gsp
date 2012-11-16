<g:hiddenField name="callerId" value="1234" />
<div class="control-group ${hasErrors(bean: shoppeUserInstance, field: 'companyName', 'error')}">
	<label for="companyName" class="control-label">
		<g:message code="shoppeUser.companyName.label" default="Company Name" />
	</label>
	<div class="controls">
		<g:textField name="companyName" class="input-large" required="required" value="${shoppeUserInstance?.companyName}"/>
	</div>
</div>
<div class="control-group ${hasErrors(bean: shoppeUserInstance, field: 'firstName', 'error')}">
	<label for="firstName" class="control-label">
		<g:message code="shoppeUser.firstName.label" default="First Name" />
	</label>
	<div class="controls">
		<g:textField name="firstName" class="input-large" required="required" value="${shoppeUserInstance?.firstName}"/>
	</div>
</div>
<div class="control-group ${hasErrors(bean: shoppeUserInstance, field: 'lastName', 'error')}">
	<label for="" class="control-label">
		<g:message code="shoppeUser.lastName.label" default="Last Name" />
	</label>
	<div class="controls">
		<g:textField name="lastName" class="input-large" required="required" value="${shoppeUserInstance?.lastName}"/>
	</div>
</div>
<div class="control-group ${hasErrors(bean: shoppeUserInstance, field: 'email', 'error')}">
	<label for="" class="control-label">
		<g:message code="shoppeUser.email.label" default="Email" />
	</label>
	<div class="controls">
		<g:textField name="email" class="input-xlarge" required="required" value="${shoppeUserInstance?.email}"/>
	</div>
</div>
<div class="control-group ${hasErrors(bean: shoppeUserInstance, field: 'magentoStoreURI', 'error')}">
	<label for="magentoStoreURI" class="control-label">
		<g:message code="shoppeUser.magentoStoreURI.label" default="Magento Store URI" />
	</label>
	<div class="controls">
		<g:textField name="magentoStoreURI" class="input-xxlarge" required="required" value="${shoppeUserInstance?.magentoStoreURI ?: 'http://ec2-107-22-49-30.compute-1.amazonaws.com/magento/index.php/'}" />
	</div>
</div>
<div class="control-group ${hasErrors(bean: shoppeUserInstance, field: 'apiKey', 'error')}">
	<label for="email" class="control-label">
		<g:message code="shoppeUser.apiKey.label" default="API Key" />
	</label>
	<div class="controls">
		<g:textField name="apiKey" class="input-xxlarge" required="required" value="${shoppeUserInstance?.apiKey ?: 'k6jcg57urgrpx3tdocwehmk7jnytvyfv'}" />
	</div>
</div>
<div class="control-group ${hasErrors(bean: shoppeUserInstance, field: 'sharedSecret', 'error')}">
	<label for="" class="control-label">
		<g:message code="shoppeUser.sharedSecret.label" default="Shared Secret" />
	</label>
	<div class="controls">
		<div class="input-append">
			<g:textField name="sharedSecret" class="input-xxlarge" required="required" value="${shoppeUserInstance?.sharedSecret ?: 'j7epse81i2nmkv95e2l5ozbg5vb2hvqp'}" /><button class="oauth-verify btn btn-small" type="button" style="padding: 4px 9px;">Verify</button>
		</div>
	</div>
</div>
<div class="control-group ${hasErrors(bean: shoppeUserInstance, field: 'verifierAuthCode', 'error')}">
	<label for="" class="control-label">
		<g:message code="shoppeUser.verifierAuthCode.label" default="Verification Code" />
	</label>
	<div class="controls">
		<g:textField name="verifierAuthCode" class="input-xxlarge" required="required" value="${shoppeUserInstance?.verifierAuthCode}" />
	</div>
</div>
<div class="control-group">
	<div class="controls">
		* All fields are required
	</div>
</div>
<g:link controller="verify" action="index" class="oauth-link hidden" />
