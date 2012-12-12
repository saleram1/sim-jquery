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
  Boolean colorAppendedToSKU
  String colorAttributeName
  String description
  // errs.size
  Integer errorItemsCount
  String htmlDescription
  String listingType
  String meliCategory
  Boolean sizeAppendedToSKU
  String sizeAttributeName
  String status = "PENDING"
  Double  stockPercentage
  Integer storeCategory
  Integer totalItemsCount = 100
  Integer validItemsCount = 0    // listings.size

  Date lastUpdated
  Date dateCreated
  Date dateCompleted


  // Adding ItemListing hasMany
  static hasMany = [errs: ApiError, listings: ItemListing]

  static constraints = {
    colorAppendedToSKU(nullable: true)
    colorAttributeName(nullable: true, blank: false)
    dateCompleted(nullable: true)
    description(nullable: true)
    htmlDescription(nullable: true, maxSize: 64*1024)
    listingType()
    meliCategory()
    sizeAppendedToSKU(nullable: true)
    sizeAttributeName(nullable: true, blank: false)
    status(inList:  ['PENDING', 'STOPPED', 'COMPLETE'])
    stockPercentage(min: 25.0d, max: 100.0d)
    storeCategory()
  }

  static mapping = {
    htmlDescription type: "text"
  }


//GORM EVENTS
  def beforeUpdate = {
    if (status == 'COMPLETE') {
      dateCompleted = new Date()
    }
  }
}