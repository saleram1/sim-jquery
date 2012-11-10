package com.mercadolibre.apps.sim.data.bo.imports

import com.mercadolibre.apps.sim.data.bo.errors.ApiError
import grails.converters.JSON
import com.mercadolibre.apps.sim.CategoryService
import com.mercadolibre.apps.sim.ImportService
import com.mercadolibre.apps.sim.data.bo.core.VanillaItemListing

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/8/12
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
class MagentoItemImportController {

	static scaffold = true
	static allowedMethods = [startUpload: "POST", showUploadProgress: "GET", start: "GET"]

  CategoryService categoryService
  ImportService importService

  def startImport(StartMageCatalogSlurpCommand command) {
    def newItemId = importListingsFromMage(command)
    log.info "ML item listed https://api.mercadolibre.com/items/${newItemId}"

    def response = new ApiError(message: "OK", error: "", status: 200)
    println (response) as JSON

    render g.link("url": "https://api.mercadolibre.com/items/${newItemId}")
  }

  def importListingsFromMage(StartMageCatalogSlurpCommand command) {
    log.info "importListingsFromMage:  using accessToken: " + session.ml_access_token

    if (categoryService.isValidCategory(command.meliCategory)) {

      def aProduct = JSON.parse(MageSimpleProduct.productSampleProAudio) as Map

      VanillaItemListing listing = new VanillaItemListing(category_id: command.meliCategory, listing_type_id: command.listingType,
        currency_id: "ARS", price: aProduct['regular_price_without_tax'], available_quantity: aProduct['is_saleable'] as Integer,
        title: aProduct['short_description'], description: aProduct['description'], seller_custom_field: aProduct['sku'],
        condition: "new", pictureURL: aProduct['image_url']
      )

      log.info(listing as JSON)
      return importService.pushItemToMarketplace2(listing, session.ml_access_token)
    }
    else {
      log.warn "Cannot support category_id id: ${command.meliCategory} in the current version"
    }

    return null
  }
}


/*
	Each of these will trigger listings to Magentooo

	listingType=bronze
	productSelection=Selected Category Ids
	storeCategory=
	meliCategory=MLA78884
	buyingMode=buy_it_now=

*/

class StartMageCatalogSlurpCommand {
	String action
  String buyingMode
  String listingType
  String meliCategory
  String productSelection
  String storeCategory
}

final class MageSimpleProduct {
  def static productSampleProAudio =
"""
{
    "entity_id": "3",
    "type_id": "simple",
    "sku": "PBM81234",
    "name": "Tannoy PBM-8 LM Powered Studio Monitors - No Ofertar",
    "meta_title": null,
    "meta_description": null,
    "description": "<p><strong>Power, Frequency and Sensitivity Specifications <\\/strong><\\/p>\\r\\n<p>The Tannoy PBM-8 speakers have a frequency response of 45 hertz to 25 kilohertz (-10 decibel at 33 hertz), when allowing for +\\/- 3 decibel. The crossover frequency for the speakers has been measured at 1.9 kilohertz, and at their peak power they can handle 125 watts. The PBM-8s have a sensitivity of 92 decibels, or 89 decibels in an area that has a low degree of sound reverberation.<\\/p>\\r\\n<p>&nbsp;<\\/p>\\r\\n<p><strong>Size, Weight and Impedance Specifications<\\/strong><\\/p>\\r\\n<p>Tannoy's PBM-8 monitor speakers use an 8-inch woofer. A pair of the speakers weigh 23 1\\/2 pounds, while alone each weighs 11 3\\/4 pounds. The height, width, and diameter of each speaker is 16.3 inches, 10.88 inches, and 10.75 inches respectively. The speakers have an impedance of a minimum of 4 ohms and a maximum of 8 ohms.<\\/p>\\r\\n<p>The Tannoy PBM-8 speakers use a 5DR51570 tweeter, which has a 1-inch acoustic cavity silk dome that is ferro-fluid cooled. It is 4 1\\/16 inches when you measure across the front of the mounting plate. The outside diameter of the tweeter is 4.09 inches, while the diameter of the hole cut out for the cabinet is 2.845 inches. The tweeter has a peak power rating of 110 watts, a frequency range of 2,000 hertz to 24,000 hertz, and a maximum impedance of 6 ohms.<\\/p>\\r\\n<p>&nbsp;<\\/p>\\r\\n<p><strong><em>Plus (powered spec.): From Tannoy<\\/em>:<\\/strong> \\"The PBM 8 <strong>LM<\\/strong> features an 8-inch woofer and 1-inch tweeter in a rectangular cabinet weighing 23.5 pounds. (The attached Limpet amplifier weighs an additional eight pounds.) Dimensions are 10.86x15.75x14.25 inches, including the amp. Frequency response: 45 to 25k Hz, &plusmn;3 dB; max SPL: 115 dB. Input is via a Neutrik 1\\/4-inch, unbalanced\\/balanced XLR combo connector. The system operates on either 110V or 220V AC supplies.\\"<\\/p>",
    "short_description": "Tannoy PBM-8 LM Powered Studio Monitors  - No Ofertar",
    "meta_keyword": null,
    "tier_price": [],
    "is_in_stock": "1",
    "regular_price_with_tax": 375,
    "regular_price_without_tax": 375,
    "final_price_with_tax": 375,
    "final_price_without_tax": 375,
    "is_saleable": "1",
    "image_url": "http:\\/\\/ec2-107-22-49-30.compute-1.amazonaws.com\\/magento\\/media\\/catalog\\/product\\/cache\\/0\\/image\\/9df78eab33525d08d6e5fb8d27136e95\\/t\\/a\\/tannoy.jpg",
    "url": "http:\\/\\/ec2-107-22-49-30.compute-1.amazonaws.com\\/magento\\/index.php\\/catalog\\/product\\/view\\/id\\/3\\/s\\/tannoy-pbm-8-powered-monitors\\/",
    "buy_now_url": "http:\\/\\/ec2-107-22-49-30.compute-1.amazonaws.com\\/magento\\/index.php\\/checkout\\/cart\\/add\\/uenc\\/aHR0cDovL2VjMi0xMDctMjItNDktMzAuY29tcHV0ZS0xLmFtYXpvbmF3cy5jb20vbWFnZW50by9hcGkvcmVzdC9wcm9kdWN0cy8zP3R5cGU9cmVzdA,,\\/product\\/3\\/",
    "total_reviews_count": "0",
    "has_custom_options": false
}

"""
}
