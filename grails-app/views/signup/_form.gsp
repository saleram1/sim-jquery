
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

<div class="fieldcontain ${hasErrors(bean: shoppeUserInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="shoppeUser.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="email" required="" value="${shoppeUserInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: shoppeUserInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="shoppeUser.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:passwordField name="password" required="true" value="${shoppeUserInstance?.password}"/>
</div>
