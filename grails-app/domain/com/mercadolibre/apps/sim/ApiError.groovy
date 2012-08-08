package com.mercadolibre.apps.sim

import groovy.transform.ToString

/** 
 *  Example {"message":"Category not found: MLB1234","error":"not_found","status":404,"cause":[]}
 */
@ToString
class ApiError {
	Integer status = 400		// Bad request
	String error
	String message
	String cause
		
	static constraints = {
		status(min: 400, max: 499)
		error(nullable: false, blank: false)
		message(nullable: false, blank: false)
		cause(nullable: true, blank: false)
	}
}
