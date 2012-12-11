package com.mercadolibre.apps.sim.data.bo.errors

import groovy.transform.ToString

/** 
 *  Example {"message":"access_token.invalid","error":"Invalid OAuth access token.","status":403,"cause":[]}
 */
@ToString
class AccessViolationError extends ApiError {
	
	//
	// Some User objects are not setup yet to POST items - there is no error message but 
	// we re-use the one from  'bad access token' 
	// 
	AccessViolationError(String anOriginalFilename, Integer aRowNumber = 1) {
		super()
		
		status = 403
		error  = "access_token.invalid"
		message = "Invalid OAuth access token."
		originalFilename = anOriginalFilename
	}

  //
  // Sometimes a User hits their Quota for the day
  //
  AccessViolationError(String error, String message, String originalFilename = "") {
    super()

    status = 403
    this.error = error
    this.message = message
    this.originalFilename = originalFilename
  }
}