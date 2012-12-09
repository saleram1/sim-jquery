package com.mercadolibre.apps.ml

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 12/8/12
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
class MercadoLibreListingService {

  static transactional = false


  def listFashionItem(Map aProduct) {
    println ''
    println 'FUNKY LA FASHIONISTA'

    log.info aProduct
  }

  def listRegularItem(Map aProduct) {
    println 'VANILLA'

    log.info aProduct
  }
}
