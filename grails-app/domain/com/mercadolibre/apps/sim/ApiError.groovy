package com.mercadolibre.apps.sim

import groovy.transform.ToString

/** 
 *  Example {"message":"Category not found: MLB1234","error":"not_found","status":404,"cause":[]}
 */
@ToString
class ApiError {
	String originalFilename
	Integer status = 400		// Bad request
	String error
	String message
	String cause
		
	static constraints = {
		cause(nullable: true, blank: false)
		error(nullable: false, blank: false)
		message(nullable: false, blank: false)
		originalFilename(nullable: true, blank: false)
		//rowNumber(min:1, max:65536)
		status(min: 400, max: 499)
	}
	
	static mapping = {
        tablePerHierarchy false
    }	
}