<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>SIM</title>
	</head>
	<body>
		<header class="jumbotron subhead" id="overview">
		  <h1>Upload Items</h1>
		  <p class="lead">Please click the button below to begin.</p>
		</header>

		<div class="container section">
			<div class="span9">
				<div class="form-actions" id="starter-form">
				<a href="#">
					<button id="get-access-token" class="btn btn-large" type="button">
						<i class="icon-ok-sign icon-white"></i>
						<g:message code="xxx" default="Start" />
					</button>
				</a>

				<code id="access_token" style="display: none;">token</code>
				</div>
			</div>
		</div>

		<script>
		    $(document).ready(function() {
		        $('#get-access-token').click(function() {
					window.location.href = "https://auth.mercadolibre.com.ar/authorization?response_type=token&client_id=10751";
		        });
		    });
		</script>
				
		<!-- OAuth callback returns to this page with valid access_token -->		
		<script>
			var newToken = parseUri(window.location.href).anchor;
			if (newToken != '') {
				window.location.href = "/sim/authorize?" + newToken;
			}			 
		</script>		
	</body>
</html>