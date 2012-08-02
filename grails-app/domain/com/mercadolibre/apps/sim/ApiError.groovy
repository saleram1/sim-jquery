package com.mercadolibre.apps.sim

class ApiError {
	Integer status = 401
	String error
	String message
	String cause

/*		curl https://api.mercadolibre.com/pictures/MCO2557702734_032012/metadata
	{"message":"Picture metadata for id MCO2557702734_032012 not found.", "error":"not_found", "status":"404", "cause":[]}
	real	0m5.026s
*/


	static constraints = {
		status()
		error()
		message(nullable: true)
		cause(nullable: true)
	}
}