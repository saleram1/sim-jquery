
import com.mercadolibre.apps.sim.CategoryService
import com.mercadolibre.apps.sim.data.bo.core.FashionItemListing
import com.mercadolibre.apps.sim.data.bo.core.ItemVariation
import com.mercadolibre.apps.sim.data.bo.core.VanillaItemListing
import com.mercadolibre.apps.sim.data.bo.core.VariationAttribute
import com.mercadolibre.apps.sim.data.bo.errors.ApiError
import com.mercadolibre.apps.sim.data.bo.imports.MagentoStoreService
import com.mercadolibre.apps.sim.data.bo.imports.meli.Category
import com.mercadolibre.apps.sim.data.bo.imports.meli.MeliCategorySyncService

import grails.converters.JSON

/// mostly used to load custom marshallers for JSON over HttpBuilder library
class BootStrap {

  MagentoStoreService magentoStoreService
  CategoryService categoryService
  MeliCategorySyncService meliCategorySyncService

  def getBaseItemMap(it) {
    def map = [:]

    map["site_id"] = it.site_id
    map["category_id"] = it.category_id
    map["title"] = it.title
    map["price"] = it.price
    map["currency_id"] = it.currency_id
    map["buying_mode"] = it.buying_mode
    map["available_quantity"] = it.available_quantity
    map["listing_type_id"] = it.listing_type_id
    map["condition"] = it.condition
    map["description"] = it.description
    map["accepts_mercadopago"] = Boolean.TRUE
    map
  }

  def init = { servletContext ->

    JSON.registerObjectMarshaller(VariationAttribute, 3) {
      def map = [:]
      map.id = it.id
      map.value_id = it.value_id ?: ""
      map.value_name = it.value_name ?: ""
      return map
    }

    // Deep workaround...
    JSON.registerObjectMarshaller(ItemVariation, 3) {
      def map = [:]
      map.attribute_combinations = it.attribute_combinations as List
      //map.attribute_combinations.sort { o1, o2 -> o1.id <=> o2.id }
      map.price = it.price
      map.available_quantity = it.available_quantity
      map.picture_ids = it.picture_ids
      if (it.seller_custom_field && it.seller_custom_field.trim().size() > 0)
        map.seller_custom_field = it.seller_custom_field
      return map
    }

    JSON.registerObjectMarshaller(FashionItemListing, 2) {
      def map = [:]

      map["title"] = it.title
      map["description"] = it.description
      map["site_id"] = it.site_id
      map["category_id"] = it.category_id
      map["currency_id"] = it.currency_id
      map["buying_mode"] = it.buying_mode
      map["listing_type_id"] = it.listing_type_id
      map["condition"] = it.condition
      map["accepts_mercadopago"] = Boolean.TRUE

      map["variations"] = it.variations
      return map
    }


    JSON.registerObjectMarshaller(VanillaItemListing, 3) {
      def map = getBaseItemMap(it)
      def pictureSources = []
      it?.pictures?.each() { pic ->
        pictureSources.add([source: pic.toString()])
      }
      map["pictures"] = pictureSources
      //			map["pictures"] = it.pictures

      return map
    }


    // NON-Listing related like errors
    JSON.registerObjectMarshaller(ApiError, 3) {
      def map = [:]

      map.message = it.message
      map.error   = it.error
      map.status  = it.status
      map.cause   = []
      return map
    }

    JSON.registerObjectMarshaller(CategoryConfirmResponse, 3) {
      def map = [:]

      map.id = it.id
      map.categoryType = it.categoryType
      map.isFashion = it.isFashion
      return map
    }


    //CLEANUP AISLE 14
    if (!System.getProperty("meli.category.load.skip") &&
        Category.count() == 0) {
      
	  //  Check to see if we have the latest version of the category tree
	  if (!meliCategorySyncService.isMeliCategoryInSync()) {
		// TODO: clean this stuff up later, PLEASE!!!
		// pull down the latest version of the category tree zip file
		meliCategorySyncService.syncCategoryZipFile("https://api.mercadolibre.com/sites/MLA/categories/all", "/tmp/mercadoCatFile.gz")
		meliCategorySyncService.unzipCategoryZipFileFromMeli("/tmp/mercadoCatFile.gz", "/tmp/mercadoCatFile.txt")
		meliCategorySyncService.parseMeliCategoryIdsIntoFile("/tmp/mercadoCatFile.txt", "/tmp/mercadoCatFileIds.txt")
		meliCategorySyncService.saveMeliCategoryMD5()
		categoryService.loadMeliCategories()
	  }
	  //TODO:  need to modify above to call the MeliCategorySyncService.isMeliCategoryInSync() method to determine if our Meli Category tree
	  // is in sync.  if it is, we don't need to do another load on startup
	  // for now I am letting it do this on startup since we are still using an in-memory database
    }

// The Advanced requirements will use / register a Marshaller
//		JSON.registerObjectMarshaller(Item, 3) { ItemMarshaller.marshall(it) }
  }

  def destroy = {
  }
}
