package com.mercadolibre.apps.sim.data.bo.imports

import com.mercadolibre.apps.sim.data.bo.errors.ApiError
import com.mercadolibre.apps.sim.data.bo.core.ItemListing
import groovy.transform.ToString

/**
 * Record of each session to download the needed products from Store and list in ML[A,B]
 *
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/27/12
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
@ToString
class MagentoCatalogImportJob {

  String buyingMode
  Boolean colorAppendedToSKU
  String colorAttributeName
  String description
  // errs.size
  Integer errorItemsCount
  String htmlDescription
  String listingType
  String meliCategory
  String productSelection
  Boolean sizeAppendedToSKU
  String sizeAttributeName
  String status = "PENDING"
  Double  stockPercentage
  Integer storeCategory
  Integer totalItemsCount = 100
  Integer validItemsCount = 0    // listings.size

  Date dateCreated
  Date lastUpdated


  // Adding ItemListing hasMany
  static hasMany = [errs: ApiError, listings: ItemListing]

  static constraints = {
    buyingMode()
    colorAppendedToSKU(nullable: true)
    colorAttributeName(nullable: true, blank: false)
    description(nullable: true)
    htmlDescription(nullable: true)
    listingType()
    meliCategory()
    productSelection()
    sizeAppendedToSKU(nullable: true)
    sizeAttributeName(nullable: true, blank: false)
    status(inList:  ['READY', 'PENDING', 'STOPPED', 'COMPLETE'])
    stockPercentage(min: 0.0d, max: 100.0d)
    storeCategory()
  }
}
