
<!doctype html>
<html>
	<head>
		<meta name="layout" content="landing">
		<title>Help</title>
	</head>	
	<body>
		<header class="jumbotron subhead" id="overview">
		  <h1>Help</h1>
		  <p class="lead">Before creating a CSV file, you may want to review the details below.&hellip;</p>
		</header>

		<div class="container section">		
		<div id="hlpQ"><h2>Supported fields for Catalog CSV files</h2></div>
		<div></div>
		<div id="hlpA" class="article">A <em>CSV</em> (Comma Separated Values) file is a comma delimited database file. Commas are used to separate different fields in the database.  <br/> Valid fields that can be used in your Excel CSV format file are the following:
		<p>nbsp;</p>

		<ul>
			<li>gp_id</li>
			<li>code</li>
			<li>format</li>
			<li>currency</li>
			<li>price</li>
			<li>title</li>
			<li>description</li>
			<li>category</li>
			<li>pictureURL</li>
			<li>options</li>
			<li>shippingCosts</li>
			<li>available_quantity</li>
			<li>location</li>
			<li>shipWeight</li>
			<li>condition</li>
		</ul>

		<p>
			It doesn't matter what order you put the fields in, so long as the field names in the first line match the records in the remaining lines. Additionally, records don't have to contain values for all the fields in an item.
		</p>
		<p>
			nbsp;
		</p>

		<p><strong>gp_id</strong>
		This is how the store refers to your item, If you do not specify an ID, your <strong>code</strong> will be used, with some substitutions. To maintain the greatest control over your store, we strongly suggest that you specify an ID for each of your items.
		</p>

		<p><strong>format</strong>
		 	Markplace engine supports the following: <code>auction, buy_it_now</code>
		</p>

		<p><strong>currency</strong>
			Currency ISO code - e.g. ARS, BRD, USD.  See the supported currency for your <a target="_new" href="https://api.mercadolibre.com/sites/MLA/">site</a>.
		</p>

		<p><strong>price</strong>
		If price is entered, item is treated as product for sale.  Please use numerals and a single decimal point only; do not use currency symbols such as &#36;.</p>

		<p><strong>title</strong>
		Appears at the top of your visitors' browser window; also used as the text for the clickable link wherever your store appears in web search results.
		</p>

		<p><strong>description</strong>
		Text below the clickable link wherever your store appears in web search results.
		</p>

		<p><strong>category</strong>
		 Marketplace category e.g. MLA9997.  MLA for Argentina site, MLB for Brasil categories and so forth.
		</p>

		<p><strong>pictureURL</strong>
			Required by marketplace engine.  Will be uploaded to the CDN
		</p>
		
		<p><strong>options</strong>
		For items with multiple sizes, colors, etc. If the item is a gift certificate, do not enter information into this field or the order will fail. In each set of options, the first word is the label (e.g Size) and the rest are the choices (Small, Medium, Large). Use a blank line to separate sets of options, and double-quotes to make several words be treated as a multiple-word phrase. Example:
		<code>Size S M L XL<br/><br/>
		Color Black White "Sea Green"</code>
		.
		</p>

		<p><strong>location</strong>
		A brief line of description - where is item ship from
		</p>

		<p><strong>shipWeight</strong>
		How many pounds does the product weigh? Required to accurately calculate shipping costs based on weight, including UPS Real-Time Rates. Numerals and a single decimal point only.
		</p>

		<p><strong>condition</strong>
		Required by marketplace engine.  Required to be in  <code>New, Used</code> and so forth
		</p>

		<p>Please note that misnaming any of the above fields will cause the the fields to not be recognized upon upload. These field names are not case sensitive, but they must be spelled exactly as they are listed above. The order of your fields in the CSV file are not important.</p>
				</div>
		
	</body>
</html>
