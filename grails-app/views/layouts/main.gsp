<%@ page import="org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes" %>
<g:if test="${session.ml_access_token}">
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
		
		<g:layoutHead/>
		<r:layoutResources/>
	</head>
	<body>
		<nav class="navbar">
			<div class="navbar-inner">
				<img width="125" height="75" src="${resource(dir: 'images', file:'ml-black.jpg')}" alt="logo" />
			</div>
            <div class="topright">
                <g:nickNameAndCompany/>
            </div>
			<div class="navbar-inner">
				<div class="container-fluid">
					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						<span class="icon-bar"></span>
					</a>					
					<a class="brand" href="${createLink(uri: '/')}">MercadoLibre Connect (M2M)</a>
					<div class="nav-collapse">
						<ul class="nav">
                            <li<%= request.forwardURI == "${createLink(uri: '/magentoItem/list')}" ? ' class="active"' : '' %>><a href="${createLink(uri: '/magentoItem/list')}">Listings</a></li>
                            <li<%= request.forwardURI == "${createLink(uri: '/magentoCatalogImportJob/list?sort=dateCreated&max=20&order=desc')}" ? ' class="active"' : '' %>><a href="${createLink(uri: '/magentoCatalogImportJob/list?sort=dateCreated&max=20&order=desc')}">History</a></li>
                            <li>
                                <g:link target="_blank" absolute="true" url="https://questions.mercadolibre.com.ar/seller">Questions</g:link>
                            </li>
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
		    		<p>Copyright &copy; 2012 MercadoLibre S.R.L &nbsp;&nbsp; <a href="http://www.mercadolibre.com.ar/seguro_terminos.html" id="FOOT:TERMS">Terms &amp; Conditions</a></p>
				</div>
			</footer>
		</div>

		<r:layoutResources/>

		<script type="text/javascript">
			var uvOptions = {};
			 (function() {
			    var uv = document.createElement('script');
			    uv.type = 'text/javascript';
			    uv.async = true;
			    uv.src = ('https:' == document.location.protocol ? 'https://': 'http://') + 'widget.uservoice.com/VkGHCMuyF8wZm6wRjzDFQ.js';
			    var s = document.getElementsByTagName('script')[0];
			    s.parentNode.insertBefore(uv, s);
			})();
		</script>
	</body>
</html>
</g:if>

<g:else>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
<!--
function redirectToHomePage(){
	window.location = '<g:createLink uri="/" />';
}
//-->
</script>
</head>

<body onLoad="setTimeout('redirectToHomePage()', 2000);">
	<center>
		<font face="sans-serif,Helvetica,Verdana" size="+1">
			Please wait&hellip;
		</font>
	</center>
</body>
</html>
</g:else>