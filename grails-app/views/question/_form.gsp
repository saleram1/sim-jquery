<%@ page import="com.mercadolibre.apps.sim.data.bo.core.Question" %>



<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'answerDate', 'error')} ">
	<label for="answerDate">
		<g:message code="question.answerDate.label" default="Answer Date" />
		
	</label>
	<g:datePicker name="answerDate" precision="day"  value="${questionInstance?.answerDate}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'answerText', 'error')} ">
	<label for="answerText">
		<g:message code="question.answerText.label" default="Answer Text" />
		
	</label>
	<g:textField name="answerText" value="${questionInstance?.answerText}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'itemId', 'error')} ">
	<label for="itemId">
		<g:message code="question.itemId.label" default="Item Id" />
		
	</label>
	<g:textField name="itemId" value="${questionInstance?.itemId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'sellerId', 'error')} required">
	<label for="sellerId">
		<g:message code="question.sellerId.label" default="Seller Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="sellerId" required="" value="${questionInstance.sellerId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'questionId', 'error')} required">
	<label for="questionId">
		<g:message code="question.questionId.label" default="Question Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="questionId" required="" value="${questionInstance.questionId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="question.status.label" default="Status" />
		
	</label>
	<g:textField name="status" value="${questionInstance?.status}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'text', 'error')} ">
	<label for="text">
		<g:message code="question.text.label" default="Text" />
		
	</label>
	<g:textField name="text" value="${questionInstance?.text}"/>
</div>

