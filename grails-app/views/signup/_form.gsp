<g:hiddenField name="callerId" value="1234" />

<div class="fieldcontain ${hasErrors(bean: shoppeUserInstance, field: 'companyName', 'error')} required">
	<label for="companyName">
		<g:message code="shoppeUser.companyName.label" default="Company Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="companyName" required="" value="${shoppeUserInstance?.companyName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: shoppeUserInstance, field: 'firstName', 'error')} required">
	<label for="firstName">
		<g:message code="shoppeUser.firstName.label" default="First Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="firstName" required="" value="${shoppeUserInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: shoppeUserInstance, field: 'lastName', 'error')} required">
	<label for="lastName">
		<g:message code="shoppeUser.lastName.label" default="Last Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lastName" required="" value="${shoppeUserInstance?.lastName}"/>
</div>

<div class="fieldcontain  required">
	<label for="magentoStoreURI">
		Magento Store
	</label>
	<input class="input-xlarge" type="text" name="magentoStoreURI" value="http://ec2-107-22-49-30.compute-1.amazonaws.com/magento/index.php/" />
</div>

<div class="fieldcontain ${hasErrors(bean: shoppeUserInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="shoppeUser.email.label" default="Api Key" />
		<span class="required-indicator">*</span>
	</label>
	<input type="text" name="apiKey" />
</div>

<div class="fieldcontain ${hasErrors(bean: shoppeUserInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="shoppeUser.password.label" default="Shared Secret" />
		<span class="required-indicator">*</span>
	</label>
	<g:passwordField name="password" required="true" value="${shoppeUserInstance?.password}"/>
	
	<button id="newButton" class="link">
      <g:link controller="verify" action="index">Connect&hellip;</g:link>
    </button>
</div>

<input type="text" name="verifyOrAuthsCode" />


<input type="hidden" name="email" value="test301210_333@robot.com" />

<div class="fieldcontain  required">
	<label for="addy">
		Company Address
	</label>
	<g:textArea name="address1" rows="4" cols="32" />
</div>
