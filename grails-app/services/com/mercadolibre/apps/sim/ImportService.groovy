package com.mercadolibre.apps.sim

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import com.mercadolibre.apps.sim.util.CSVImporter

/**
 * Grails friendly wrapper around the CSVImporter
 *   ItemImporterService
 *
 */
class ImportService {
  static transactional = false

  CategoryService categoryService


  def importContactsFromCSV(String csvFile) {
    log.info "importContactsFromCSV: " + csvFile

    def items = CSVImporter.doImport(csvFile)
    int totalCount = 0
    int count = 0

    if (items && items instanceof List) {
      totalCount = items?.size()

      String accessToken = setupMercadoApiAccess(7418, "Spz9fcrMyPQo9cD8ZJtbdn8Kk46fy2Z3")
      log.info "Got accessToken: " + accessToken

      items.eachWithIndex { it, idx ->
        Item aProperItem = new Item(it)
		
        if (!aProperItem.validate()) {
			aProperItem.errors.each() {
				println it
			}
			return [0,0]
		}
		try {
             String newItemId = newItemToMarketplace(aProperItem, accessToken)
             aProperItem.mercadoLibreItemId = newItemId

             if (newItemId) {
               aProperItem.save(flush: true, failOnError: true)
               count++
               println aProperItem
             }
	  	  }
          catch (Throwable tr) { System.err.printStackTrace() }
		}
      }
    [count, totalCount]
  }

  /**
   * Does the item already exist in User 's history
   * @param itemRef
   * @return
   */
  Boolean itemExists(Item aProperItem) {
    def aPreviousItem = Item.createCriteria().get() {
      eq('gp_id', aProperItem?.gp_id)
    }
    if (aPreviousItem) {
      log.warn("An item was already found with id : " + aProperItem.gp_id)
    }
    return (aPreviousItem != null)
  }

  /**
   * Create an authorized token for write access to be used for /item/id
   * @param apiKey
   * @param sharedSecret
   * @return
   */
  String setupMercadoApiAccess(Integer apiKey = 7418, String sharedSecret = "Spz9fcrMyPQo9cD8ZJtbdn8Kk46fy2Z3") {
    Boolean success = false
    String appUser = null

    def builder = new HTTPBuilder("https://api.mercadolibre.com")
    builder.post(path: '/oauth/token', query: [grant_type: "client_credentials", client_id: apiKey, client_secret: sharedSecret]) { resp, json ->
      if (resp.status == 200) {
        appUser = json['access_token']
      }
    }

    // switch gears to JSON from form-encoding - all the ML calls return JSON by default
    builder.contentType = ContentType.JSON

    //
    // Attempt a read only call to prove the access_token is valid
    //
    builder.get(path: '/users/me', query: [access_token: appUser]) { resp, reader ->
      success = (resp.status == 200 && appUser)
    }

    if (success) {
      return appUser
    }
    else {
      return ""
    }
  }

  /**
   pushItemToMarketPlace transaction script :
   if Item exists already (check that it's been listed), update relevant fields

   if Item  does not exist - ie there is no mapping of gp_id and mercadolibre id - then list it and update w/ pics
   *
   * @param itemRef
   * @return /item/:id (as String) on successful POST
   */
  String newItemToMarketplace(Item itemRef, String appUser) {
    def newItemId = null

    def builder = new HTTPBuilder("https://api.mercadolibre.com")

    try {
      builder.contentType = ContentType.JSON
      builder.post(path: '/items', query: [access_token: appUser], requestContentType: groovyx.net.http.ContentType.JSON, body: itemRef.toMap()) { resp, json ->
        if (resp.status == 201) {
          System.out << json['id']
          newItemId = json['id']
        }
      }
    }
    // oddly, some of the validations return 400 (Bad Request), which is not proper given the REST API error messages
    catch (groovyx.net.http.HttpResponseException ex) {
      log.error ex.message, ex
    }

    // do not forget to call a PUT for extra pictures or descriptions as the example is wrong - http://developers.mercadolibre.com/add-pictures-item/
    //   restClient.put( path: '/items/MLAnnnnn')

    // Done
    return newItemId
  }
}
