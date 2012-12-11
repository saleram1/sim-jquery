package com.mercadolibre.apps.ml

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
import com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 12/11/12
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
class MercadoLibreItemService {

  static transactional = false


  /**
   * Post the fresh Item to the Marketplace for specified duration
   *
   * @param itemRef  - object DTO to be listed of type VanillaItemListing or FashionItemListing
   * @param appUser  - access token
   * @param importJob  - place to write errors and successful listings
   * @return
   */
  def postItemToMarketplaceEx(String sku, Object itemRef, String appUser, MagentoCatalogImportJob importJob,
                                 Integer duration = 60) {
    Object gormObject

    // one HTTPBuilder customised to work w/ Grails converters
    def builder = getHttpBuilderInstance()
    builder.request("https://api.mercadolibre.com",
        groovyx.net.http.Method.POST,
        groovyx.net.http.ContentType.JSON) {
      uri.path = '/items'
      uri.query = [access_token: appUser]
      body = itemRef

      response.success = { resp, json ->
        String newItemId = json['id']
        gormObject = new ItemListing(mercadoLibreItemId: newItemId)
        log.info "Successfully listed on Site MLA -> ${newItemId}"
      }
      response.failure = { resp, json ->
        gormObject = new ApiError(status: json['status'], error: json['error'], message: json['message'])
        (gormObject as ApiError).cause = ""
        json['cause'].each() {
          gormObject.cause = gormObject.cause + " " + it
        }
        log.error gormObject
      }
      response.'402' = { resp, json ->
        gormObject = new NewUserListingUnsupportedError(importJob.listingType)
        log.error gormObject.toString()
      }
      response.'403' = { resp, json ->
        gormObject = new AccessViolationError(json['error'], json['message'])
        log.error gormObject.toString()
      }
    }
    return gormObject
  }


  /**
   * Create an instance of HttpBuilder pre-configured to play nicely with Grails encode/decode of JSON
   * Thank you, Luke Daley
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
}