
<%@ page import="com.mercadolibre.apps.sim.data.bo.core.Question" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li class="active">
							<g:link class="list" action="list">
								<i class="icon-list icon-white"></i>
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
					<h1><g:message code="default.list.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>
				
				<table class="table table-striped">
					<thead>
						<tr>
						
							<g:sortableColumn property="answerDate" title="${message(code: 'question.answerDate.label', default: 'Answer Date')}" />
						
							<g:sortableColumn property="answerText" title="${message(code: 'question.answerText.label', default: 'Answer Text')}" />
						
							<g:sortableColumn property="itemId" title="${message(code: 'question.itemId.label', default: 'Item Id')}" />
						
							<g:sortableColumn property="sellerId" title="${message(code: 'question.sellerId.label', default: 'Seller Id')}" />
						
							<g:sortableColumn property="questionId" title="${message(code: 'question.questionId.label', default: 'Question Id')}" />
						
							<g:sortableColumn property="status" title="${message(code: 'question.status.label', default: 'Status')}" />
						
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${questionInstanceList}" var="questionInstance">
						<tr>
						
							<td><g:formatDate date="${questionInstance.answerDate}" /></td>
						
							<td>${fieldValue(bean: questionInstance, field: "answerText")}</td>
						
							<td>${fieldValue(bean: questionInstance, field: "itemId")}</td>
						
							<td>${fieldValue(bean: questionInstance, field: "sellerId")}</td>
						
							<td>${fieldValue(bean: questionInstance, field: "questionId")}</td>
						
							<td>${fieldValue(bean: questionInstance, field: "status")}</td>
						
							<td class="link">
								<g:link action="show" id="${questionInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${questionInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
