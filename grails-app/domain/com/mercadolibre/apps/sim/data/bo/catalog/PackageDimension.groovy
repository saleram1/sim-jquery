package com.mercadolibre.apps.sim.data.bo.catalog

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 10/9/12
 * Time: 6:29 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This is for sizing information. We accept the following sub-fields
     Length (in inches)
     Width (in inches)
     Height (in inches)
     Volume
 */
class PackageDimension {
  Double length
  Double width
  Double height
  Double volume
  String unitOfMeasure
  String source


  PackageDimension(Double length, Double width, Double height, String unitOfMeasure = "inches", source = "MERCHANT") {
    this.length = length
    this.width  = width
    this.height = height
    this.volume = length * width * height
  }

  //Double getVolume() {}
}
