
<%@ page import="com.mercadolibre.apps.sim.data.bo.core.Question" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								<g:message code="default.list.label" args="[entityName]" />
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								<g:message code="default.create.label" args="[entityName]" />
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">

				<div class="page-header">
					<h1><g:message code="default.show.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<dl>
				
					<g:if test="${questionInstance?.answerDate}">
						<dt><g:message code="question.answerDate.label" default="Answer Date" /></dt>
						
							<dd><g:formatDate date="${questionInstance?.answerDate}" /></dd>
						
					</g:if>
				
					<g:if test="${questionInstance?.answerText}">
						<dt><g:message code="question.answerText.label" default="Answer Text" /></dt>
						
							<dd><g:fieldValue bean="${questionInstance}" field="answerText"/></dd>
						
					</g:if>
				
					<g:if test="${questionInstance?.itemId}">
						<dt><g:message code="question.itemId.label" default="Item Id" /></dt>
						
							<dd><g:fieldValue bean="${questionInstance}" field="itemId"/></dd>
						
					</g:if>
				
					<g:if test="${questionInstance?.sellerId}">
						<dt><g:message code="question.sellerId.label" default="Seller Id" /></dt>
						
							<dd><g:fieldValue bean="${questionInstance}" field="sellerId"/></dd>
						
					</g:if>
				
					<g:if test="${questionInstance?.questionId}">
						<dt><g:message code="question.questionId.label" default="Question Id" /></dt>
						
							<dd><g:fieldValue bean="${questionInstance}" field="questionId"/></dd>
						
					</g:if>
				
					<g:if test="${questionInstance?.status}">
						<dt><g:message code="question.status.label" default="Status" /></dt>
						
							<dd><g:fieldValue bean="${questionInstance}" field="status"/></dd>
						
					</g:if>
				
					<g:if test="${questionInstance?.text}">
						<dt><g:message code="question.text.label" default="Text" /></dt>
						
							<dd><g:fieldValue bean="${questionInstance}" field="text"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${questionInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${questionInstance?.id}">
							<i class="icon-pencil"></i>
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>
						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<g:message code="default.button.delete.label" default="Delete" />
						</button>
					</div>
				</g:form>

			</div>

		</div>
	</body>
</html>
