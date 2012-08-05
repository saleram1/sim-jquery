<%@ page import="org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes" %>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title><g:layoutTitle default="${meta(name: 'app.name')}"/></title>
		<meta name="description" content="">
		<meta name="author" content="">
		<meta name="viewport" content="initial-scale = 1.0">

		<r:require modules="bootstrap"/>
		<r:require modules="bootstrap-file-upload"/>
		<link rel="stylesheet" href="${resource(dir:'css', file:'customised.css')}" type="text/css">

		<!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<script src="http://static.mlstatic.com/org-img/sdk/mercadolibre-1.0.2.js"></script>
		
		<g:layoutHead/>
		<r:layoutResources/>
	</head>
	<body>
		<nav class="navbar">
			<div class="navbar-inner">
				<img width="125" height="75" src="${resource(dir: 'images', file:'ml-black.jpg')}" alt="logo" />
			</div>
			<div class="navbar-inner">
				<div class="container-fluid">
					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						<span class="icon-bar"></span>
					</a>					
					<a class="brand" href="${createLink(uri: '/')}">Simple Inventory Mgmt (SIM)</a>
					<div class="nav-collapse">
						<ul class="nav">
							<li<%= request.forwardURI == "${createLink(uri: '/help')}" ? ' class="active"' : '' %>><a href="${createLink(uri: '/help')}">Help</a></li>

						</ul>
					</div>
				</div>
			</div>	
		</nav>

		<div class="container-fluid">
			<g:layoutBody/>
			<hr>
			<footer>
				<div class="container" style="background-color:yellow;">
					<p>&copy; MercadoLibre S.R.L. 2012</p><br/>
				</div>
			</footer>
		</div>

		<r:layoutResources/>
	</body>
</html>