
<!doctype html>
<html>
	<head>
		<meta name="layout" content="landing">
		<title>Help</title>
	</head>	
	<body>
		<header class="jumbotron subhead" id="overview">
		  <h1>Help</h1>
		  <p class="lead">Before creating your CSV file, please review the following field-level details below.</p>
		</header>

		<div class="container section">		
		<div id="hlpQ"><h2>Supported fields for Catalog CSV files</h2></div>
		<div></div>
		<div id="hlpA" class="article">A <em>CSV</em> (Comma Separated Values) file is a comma delimited database file. Commas are used to separate different fields in the database.  <br/> Valid fields that can be used in your Excel CSV format file are the following:

		<ul>
			<li>gp_id</li>
			<li>site</li>
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
			<li>countryOfOrigin</li>
			<li>shipWeight</li>
			<li>condition</li>
		</ul>

		<p>&nbsp;</p>

		<p>
			It doesn't matter which order the fields are in, so long as the field names in the first/header line match the records in the remaining lines. Additionally, records don't have to contain values for all the fields in an item.
		</p>
		
		<hr/>
		
		<p>
			<h2>Details</h2>
		</p>

		<p><strong>gp_id</strong>
		This is how the store refers to your item, If you do not specify an ID, your <strong>code</strong> will be used, with some substitutions. To maintain the greatest control over your store, we strongly suggest that you specify an ID for each of your items.
		</p>

		<p><strong>site</strong>
		 	Markplace site Id - 'MLA' for Argentina site, 'MLB' for Brasil, 'MLC' for Chile etc.</code>
		</p>

		<p><strong>format</strong>
		 	Markplace engine supports the following codes: <code>auction, buy_it_now</code>
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
			Marketplace category. Where in the category tree listing will appear in Search - e.g. Zapatos e Botas ==> MLA9997.  Categories will start with 'MLA' for Argentina site, 'MLB' for Brasil, 'MLC' for Chile categories and so forth.  Check out example listings using <a href="https://api.mercadolibre.com/sites/MLA/search?category=MLA9997">this</a> api call.  
		</p>

		<p><strong>pictureURL</strong>
			Required by marketplace engine.  Pictures will be copied and uploaded to our CDN.
		</p>
		
		<p><strong>options</strong>
		For items with multiple sizes, colors, etc this field is required.  In each set of options, the first word is the label (e.g Size) and the rest are the choices (36, 38, 42). Use a blank line to separate sets of options, and double-quotes to make several words be treated as a multiple-word phrase. Example:
		<code>Size 36 38 40 42</code><br/>
		</p>

		<p><strong>location</strong> (optional)
			A brief line of description - where is item shipped from
		</p>

		<p><strong>countryOfOrigin</strong> (optional)
			A brief line of description - where is item shipped from
		</p>

		<p><strong>shipWeight</strong> (optional)
		How many pounds does the product weigh? Required to accurately calculate shipping costs based on weight, including UPS Real-Time Rates. Numerals and a single decimal point only.
		</p>

		<p><strong>condition</strong>
			Required by marketplace engine.  Required to be in  <code>new, used</code>.  These are the codes supported at present.
		</p>

		<p>Please note that misnaming any of the above fields will cause the Items not be recognized upon upload. These field names are not case sensitive, but they must be spelled exactly as listed above. The order of your fields in the CSV file are not important.
		</p>
		
	</div>	
	</body>
</html>
