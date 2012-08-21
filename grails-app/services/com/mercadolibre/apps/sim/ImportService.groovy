package com.mercadolibre.apps.sim

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import com.mercadolibre.apps.sim.util.CSVImporter

/**
 *
 * Check if Item exists already (check that it's been listed), update relevant fields
 *          Item  does not exist - ie there is no mapping of gp_id and mercadolibre id - then list it and update w/ pics
 *
 */
class ImportService {
  static transactional = true

  CategoryService categoryService

  def importContactsFromCSV(ItemImport itemImport, ItemImportFileSource fileSource, String accessToken = "") {
    //String accessToken = setupMercadoApiAccess(7418, "Spz9fcrMyPQo9cD8ZJtbdn8Kk46fy2Z3")
    log.info "importContactsFromCSV: " + fileSource.path + " orig. " + fileSource.originalFilename
    log.info "Using accessToken: " + accessToken

    String csvFile = fileSource.path
    def items = CSVImporter.doImport(csvFile)

    int totalCount = 0
    int count = 0

    if (items && items instanceof List) {
      totalCount = items?.size()

      items.eachWithIndex { it, idx ->
        Item aProperItem = newItemFromMap(it)

        if (!itemExists(aProperItem) && aProperItem.validate()) {
          try {
            // @TODO support a 'required attribute' message in the output of ItemImport - such as options for Fashion

            // if user has not already uploaded and the Category is okay
            if (categoryService.isValidCategory(aProperItem.category) && !categoryService.isFunkyFashionFootwearCategory(aProperItem.category)) {
              String newItemId = pushItemToMarketplace(itemImport, fileSource, idx, aProperItem, accessToken)
              aProperItem.mercadoLibreItemId = newItemId

              if (newItemId) {
                count++
                log.info(aProperItem.save(flush: true, failOnError: true))
              }
            }
            else {
              log.warn "Cannot support category id: ${aProperItem.category} in the current version"
            }
          }
          catch (Throwable tr) {
            tr.printStackTrace()
          }
        }
        else if (itemExists(aProperItem)) {
          log.warn("An item was already found with id : " + aProperItem.gp_id)
          log.warn("Update the quantity to new available_quantity !!") // @TODO
        }
        //
      } // for each Item
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
      cache(true)
    }

    return (aPreviousItem != null)
  }

  /**
     pushItemToMarketPlace transaction script :
   *
   * @TODO pass the rowNumber to the ApiError Object
   *
   * @param itemRef
   * @return /item/:id (as String) on successful POST
   */
  String pushItemToMarketplace(ItemImport itemImport, ItemImportFileSource fileSource, Integer rowNumber, Item itemRef, String appUser) {
    def newItemId = null

    def builder = new HTTPBuilder()

    builder.contentType = ContentType.JSON
    builder.request("https://api.mercadolibre.com",
        groovyx.net.http.Method.POST,
        groovyx.net.http.ContentType.JSON) {
      uri.path = '/items'
      uri.query = [access_token: appUser]
      body = itemRef.toMap()

      response.success = { resp, json ->
        newItemId = json['id']
      }
      response.failure = { resp, json ->
        assert json.size() == 4
        def anException = new ApiError(status: json['status'], error: json['error'], message: json['message'], originalFilename: fileSource.originalFilename)
        anException.save()
        itemImport.addToErrs(anException)
      }
      response.'403' = { resp, json ->
        assert json.size() == 4
        def anError = new AccessViolationError(fileSource.originalFilename)
        anError.save()
        itemImport.addToErrs(anError)
      }

    }
    newItemId
  }


  def newItemFromMap(props) {
    Item anItem = new Item()
    props?.each() { key, val ->
      try {
        anItem."$key" = val
      } catch (org.codehaus.groovy.runtime.typehandling.GroovyCastException gce) {
        // property is null
      }
    }
    return anItem
  }

  /**
   * Create an authorized token for write access to be used for /item/id   - this is to be used when the server
   * requires access without the JS tools
   *
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
}