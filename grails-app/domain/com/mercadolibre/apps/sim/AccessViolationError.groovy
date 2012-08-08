package com.mercadolibre.apps.sim

/** 
 *  Example {"message":"access_token.invalid","error":"Invalid OAuth access token.","status":403,"cause":[]}
 */
class AccessViolationError extends ApiError {
	//
	// Some User objects are not setup yet to POST items - there is no error message but 
	// we re-use the one from the 'bad access token' one
	// 
	AccessViolationError() {
		super()
		
		status = 403
		error  = "access_token.invalid"
		message = "Invalid OAuth access token."
	}
}