<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>SIM</title>
	</head>
	<body>
		<header class="jumbotron subhead" id="overview">
		  <h1>Upload Items</h1>
		</header>

		<div class="container section">
			<div class="span9">
				<a href="#">
					<button class="btn btn-large" type="button" name="get-access-token" id="get-access-token">
						<i class="icon-ok-sign icon-white"></i>
						<g:message code="xxx" default="Start" />
					</button>
				</a>

				<code id="access_token" style="display: none;">token</code>
			</div>
		</div>
		
		<script>
		    $(document).ready(function() {
		        MELI.init({client_id: 10751});
		        MELI.getLoginStatus();

		        $('#get-access-token').click(function() {
					alert('Got you!');
					window.location.href = "https://auth.mercadolibre.com.ar/authorization?response_type=token&client_id=10751"
		        });

		        $('#show-my-info').click(function() {
		            if(!MELI.getToken()) {
		                MELI.login(function() {
		                    MELI.get('/users/me', null, function(data) {
		                        $('#show-my-info').hide();
		                        $('#me').html(JSON.stringify(data[2]));
		                        $('#me').show();
		                    });
		                });
		            } else {
		                MELI.get('/users/me', null, function(data) {
		                    $('#show-my-info').hide();
		                    $('#me').html(JSON.stringify(data[2]));
		                    $('#me').show();
		                });
		            }
		        });
		    });
		</script>
		
		<script>
			alert("URL --> " + parseUri(window.location.href).anchor);
		</script>		
	</body>
</html>