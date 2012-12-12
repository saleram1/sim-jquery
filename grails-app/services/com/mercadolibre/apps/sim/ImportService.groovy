package com.mercadolibre.apps.sim

import com.mercadolibre.apps.sim.data.bo.core.VanillaItemListing
import com.mercadolibre.apps.sim.util.CSVImporter
import com.mercadolibre.apps.sim.data.bo.core.ItemListing

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ParserRegistry
import groovyx.net.http.ContentType
import static groovyx.net.http.ContentType.JSON

import org.apache.http.entity.StringEntity

import org.apache.http.message.BasicHeader
import com.mercadolibre.apps.sim.data.bo.errors.AccessViolationError
import com.mercadolibre.apps.sim.data.bo.errors.ApiError
import com.mercadolibre.apps.sim.data.bo.errors.NewUserListingUnsupportedError
import com.mercadolibre.apps.sim.data.bo.imports.ItemImport
import com.mercadolibre.apps.sim.data.bo.imports.ItemImportFileSource

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
        //Item aProperItem = newItemFromMap(it)
        ItemListing aProperItem = newItemFromMap(it)
        VanillaItemListing properListing = new VanillaItemListing(it)

        // WARN only
        if (itemExists(aProperItem)) {
          log.warn("An item was already found in catalog with gp_id : " + aProperItem.gp_id)
        }

        if (!aProperItem.validate()) {
          aProperItem.errors.fieldErrors.each() {
            log.error it
          }
        }
        else {
          try {
            // @TODO support a 'required attribute' message in the output of ItemImport - such as options for Fashion

            // if user has not already uploaded and the Category is okay
            if (categoryService.isValidCategory(aProperItem.category_id) && !categoryService.isFunkyFashionFootwearCategory(aProperItem.category_id)) {
              //String newItemId = pushItemToMarketplace(itemImport, fileSource, idx, aProperItem, accessToken)
              String newItemId = pushItemToMarketplace(itemImport, fileSource, idx, properListing, accessToken)
              aProperItem.mercadoLibreItemId = newItemId

              if (newItemId) {
                count++
                log.info(aProperItem.save(flush: true))
                itemImport.listings.add(aProperItem)
              }
              else {
                log.error("New Item was not listed in MLA/MLB")
              }
            }
            else {
              log.warn "Cannot support category_id id: ${aProperItem.category_id} in the current version"
            }
          }
          catch (Throwable tr) {
            tr.printStackTrace()
          }
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
  Boolean itemExists(ItemListing aProperItem) {
    def aPreviousItem = ItemListing.createCriteria().list() {
      eq('gp_id', aProperItem?.gp_id)
      cache(true)
    }

    return (aPreviousItem?.size() > 0)
  }

  /**
   pushItemToMarketPlace transaction script :
   *
   * @TODO pass the rowNumber to the ApiError Object
   *
   * @param itemRef
   * @return /item/:id (as String) on successful POST
   */
  String pushItemToMarketplace(ItemImport itemImport, ItemImportFileSource fileSource, Integer rowNumber, Object itemRef, String appUser) {
    def newItemId = null

    // one HTTPBuilder customised to work w/ Grails converters
    def builder = getHttpBuilderInstance()
    builder.request("https://api.mercadolibre.com",
        groovyx.net.http.Method.POST,
        groovyx.net.http.ContentType.JSON) {
      uri.path = '/items'
      uri.query = [access_token: appUser]
      body = itemRef

      response.success = { resp, json ->
        newItemId = json['id']
      }
      response.failure = { resp, json ->
        //assert json.size() == 4
        def anException = new ApiError(status: json['status'], error: json['error'], message: json['message'], cause: json['cause'] ?: "",
            originalFilename: fileSource.originalFilename)
        log.error anException
        anException.save()
        itemImport.addToErrs(anException)
      }
      response.'402' = { resp, json ->
        def anError = new NewUserListingUnsupportedError(fileSource.originalFilename, itemRef.listing_type_id)
        log.error anError
        anError.save()
        itemImport.addToErrs(anError)
      }
      response.'403' = { resp, json ->
        def anError = new AccessViolationError(fileSource.originalFilename)
        log.error anError
        anError.save()
        itemImport.addToErrs(anError)
      }
    }

    newItemId
  }


  /**
   pushItemToMarketPlace transaction script :
   *
   * @TODO pass the rowNumber to the ApiError Object
   *
   * @param itemRef
   * @return /item/:id (as String) on successful POST
   */
  String pushItemToMarketplace2(Object itemRef, String appUser) {
    def newItemId = null

    // one HTTPBuilder customised to work w/ Grails converters
    def builder = getHttpBuilderInstance()
    builder.request("https://v1.api.mercadolibre.com",
        groovyx.net.http.Method.POST,
        groovyx.net.http.ContentType.JSON) {
      uri.path = '/items'
      uri.query = [access_token: appUser]
      body = itemRef

      response.success = { resp, json ->
        newItemId = json['id']
      }
      response.'204' = { resp, json ->
        newItemId = "HTTP/1.1 204 No Content"
      }
      response.failure = { resp, json ->
        def anException = new ApiError(status: json['status'], error: json['error'], message: json['message'], cause: json['cause'] ?: "",
            originalFilename: null)
        log.error anException
      }
    }
    newItemId
  }

  /**
   * Create an instance of HttpBuilder pre-configured to play nicely with Grails encode/decode of JSON
   * @see
   *
   * @return HttpBuilder
   */
  def getHttpBuilderInstance() {
    def builder = new HTTPBuilder()

    // for each JSON content type, install the handler
    JSON.contentTypeStrings.each {
      builder.parser."$it" = jsonParser
      builder.encoder."$it" = jsonEncoder
    }
    return builder
  }


  // the closure that takes a HttpServletResponse and returns an object
  def jsonParser = { response ->
    grails.converters.JSON.parse(new InputStreamReader(response.entity.content, ParserRegistry.getCharset(response)))
  }

  // the closure that takes an object and returns an org.apache.http.HttpEntity
  def jsonEncoder = { value ->
    def json = value instanceof Closure ? new grails.web.JSONBuilder().build(value) : new grails.converters.JSON(value)

    def entity = new StringEntity(json as String, "UTF-8")
    entity.contentType = new BasicHeader("Content-Type", JSON.toString())
    entity
  }


  //REMOVE
  def newItemFromMap(props) {
    ItemListing anItem = new ItemListing()
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