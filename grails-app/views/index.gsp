<!doctype html>
<html>
	<head>
		<meta name="layout" content="landing">
		<title>SIM</title>
	</head>
	<body>
		<header class="jumbotron subhead" id="overview">
		  <h1>Items Uploader</h1>
		  <p class="lead">Please select your country and click the button to begin.</p>
		</header>

		<div class="container section">
			<div class="span9">

			<div id="SITES" class="nav"> 
				<select name="site" id="site" class="countries">
					<option value="null">Selecciona un país...</option>
					<option data-id="MLA" value="https://auth.mercadolibre.com.ar">Argentina</option>
					<option data-id="MLB" value="https://auth.mercadolivre.com.br">Brasil</option>
					<option data-id="MLC" value="https://auth.mercadolibre.cl">Chile</option>
					<option data-id="MCO" value="https://auth.mercadolibre.com.co">Colombia</option>
					<option data-id="MCR" value="https://auth.mercadolibre.co.cr">Costa Rica</option>
					<option data-id="MRD" value="https://auth.mercadolibre.com.do">Dominicana</option>
					<option data-id="MEC" value="https://auth.mercadolibre.com.ec">Ecuador</option>
					<option data-id="MLM" value="https://auth.mercadolibre.com.mx">Mexico</option>
					<option data-id="MPA" value="https://auth.mercadolibre.com.pa">Panam&aacute;</option>
					<option data-id="MPE" value="https://auth.mercadolibre.com.pe">Per&uacute;</option>
					<option data-id="MPT" value="https://auth.mercadolivre.pt">Portugal</option>
					<option data-id="MLU" value="https://auth.mercadolibre.com.uy">Uruguay</option>
					<option data-id="MLV" value="https://auth.mercadolibre.com.ve">Venezuela</option>
				</select>
			</div>
									
				<div class="form-actions" id="starter-form">
				<a href="#">
					<button id="get-access-token" class="btn btn-large" type="button">
						<i class="icon-ok-sign icon-white"></i>
						<g:message code="xxx.yyy" default="Sign in" />
					</button>
				</a>

				<code id="access_token" style="display: none;">token</code>
				</div>
			</div>
		</div>

		<script>
        function getCountryValue() {
            if (document.getElementById("site").value == "null") {
				return "http://auth.mercadolibre.com";
			} else {
				return document.getElementById("site").value;
			}
        }

		$(document).ready(function() {
		    $('#get-access-token').click(function() {
		        window.location.href = getCountryValue() + "/authorization?response_type=token&client_id=12770";
		    });
		});
		</script>
				
		<!-- OAuth callback returns to this page with valid access_token -->		
		<script>
			var newToken = parseUri(window.location.href).anchor;
			if (newToken != '') {
				window.location.href = "${createLink(controller: 'itemImportFileSource', action: 'authorize', absolute: true)}" + '?' + newToken;
			}			 
		</script>		
	</body>
</html>
