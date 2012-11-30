<!doctype html>
<html>
	<head>
		<meta name="layout" content="landing">
		<title>Help</title>
	</head>	
	<body>
		<header class="jumbotron subhead" id="overview">
		  <h1>Help</h1>
		  <p class="lead"></p>
		</header>

		<div class="container section">		
		<div id="hlpQ"><h2>Supported fields for Magento Community Stores</h2></div>
		<div></div>

		<ul>
			<li>sku</li>
			<li>_category</li>
			<li>currency</li>
			<li>price</li>
			<li>title</li>
			<li>description</li>
			<li>pictureURLs</li>
			<li>available_quantity</li>
		</ul>


		<p>Please note that misnaming any of the above fields will cause your Items not be recognized upon upload. These field names are not case sensitive, but they must be spelled exactly as listed above.<br/>
			It doesn't matter which order the fields are in, so long as the field names are defined in the attribute set. Please contact your Magento admin to setup and ensure these fields are present.
		</p>
		
		<hr/>
		
		<p><strong>sku</strong>
		This is how the store refers to your item, such as a UPC code. To maintain the greatest control over your listings, we strongly suggest that you specify an ID for each of your items listed.
		</p>
		<p><strong>category</strong>
			Store, not Marketplace category. Where in the category tree listing will appear in Search - e.g. Zapatos e Botas
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

		<p><strong>pictureURL</strong>
			Required by marketplace engine.  Pictures will be copied and uploaded to our CDN.
		</p>
		
		<p><strong>variations</strong>
		For items with multiple sizes, colors, etc this field is required.  In each set of options, the first part of the sku is the 'core' or base (e.g 106539) and the rest are the sizes (36, 38, 42).
		</p>

		<p>
			<h2>MercadoLibre Marketplace Fields</h2>
		</p>

		<p><strong>condition</strong>
			Required by marketplace engine.  Required to be in  <code>new, used</code>.  These are the codes supported at present.
		</p>

		<p><strong>listing_type</strong>
			Required by marketplace.  Required to be in <code>auction, buy_it_now</code>.  These are the codes and default is 'buy_it_now'.
		</p>

		<p><strong>listing_level</strong>
		 	Marketplace site specific listing level, from lowest to highest in terms of listing fees.  Please choose from <code>bronze, silver, gold, extra_gold</code> - defaults to 'bronze'.
		</p>
	</div>	
	</body>
</html>
