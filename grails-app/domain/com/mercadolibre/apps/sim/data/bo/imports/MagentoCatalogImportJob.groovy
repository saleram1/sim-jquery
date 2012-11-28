package com.mercadolibre.apps.sim.data.bo.imports

import com.mercadolibre.apps.sim.data.bo.errors.ApiError
import com.mercadolibre.apps.sim.data.bo.core.ItemListing

/**
 * Record of each session to download the needed products from Store and list in ML[A,B]
 *
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/27/12
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
class MagentoCatalogImportJob {

  String buyingMode
  Boolean colorAppendedToSKU = false
  String colorAttributeName
  String description
  String listingTypeId
  String meliCategory
  String productSelection
  Boolean sizeAppendedToSKU  = false
  String sizeAttributeName
  String status = "PENDING"
  Long storeCategory

  // errs.size
  Integer errorItemsCount = 0

  // listings.size
  Integer validItemsCount = 0

  Date dateCreated
  Date lastUpdated


  // Adding ItemListing hasMany
  static hasMany = [errs: ApiError, listings: ItemListing]

  static constraints = {
    buyingMode()
    colorAppendedToSKU(nullable: true)
    colorAttributeName(nullable: true, blank: false)
    description(nullable: true)
    listingTypeId()
    meliCategory()
    productSelection()
    sizeAppendedToSKU(nullable: true)
    sizeAttributeName(nullable: true, blank: false)
    status(inList:  ['READY', 'PENDING', 'STOPPED', 'COMPLETE'])
    storeCategory()
  }
}