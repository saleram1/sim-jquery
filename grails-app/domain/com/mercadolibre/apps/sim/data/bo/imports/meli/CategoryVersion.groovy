package com.mercadolibre.apps.sim.data.bo.imports.meli

class CategoryVersion {

  String md5

  static constraints = {
	md5(nullable: false)
  }

  String toString() {
	return "Current version of the category tree: " + md5
  }
}
