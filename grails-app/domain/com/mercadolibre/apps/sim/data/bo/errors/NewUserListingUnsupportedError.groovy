package com.mercadolibre.apps.sim.data.bo.errors

import groovy.transform.ToString

/**
 *  Example - from cURL -v
 *    402 Payment Required
 *
 *  Some User objects are not setup yet to POST items - there is no error message but
 *  there is a bad header
 */
@ToString
class NewUserListingUnsupportedError extends ApiError {

	// there may be several reasons User cannot list
  NewUserListingUnsupportedError(String listingTypeId) {
    super()

    status = 402
    error  = "listing_type_id.not_supported"
    message = "Cannot support listing type ${listingTypeId} for this User.  Please change listing type to 'bronze'"
    originalFilename = ""
  }

  NewUserListingUnsupportedError(String anOriginalFilename, String listingTypeId, Integer aRowNumber = 1) {
    super()

    status = 402
    error  = "listing_type_id.not_supported"
    message = "Cannot support listing type of ${listingTypeId} for this User.  Please change listing type to 'bronze'"
    originalFilename = anOriginalFilename
  }
}