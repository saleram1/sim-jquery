package com.mercadolibre.apps.sim.integration.fotter

import org.apache.commons.logging.LogFactory

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 12/11/12
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
class FotterMagicDecoder {

  static def log = LogFactory.getLog(this)


  static String decodeFotterMarcaOrBrand(marcaId) {
    if (marcaId instanceof Map && (marcaId as Map).containsKey("value")) {
      Integer marcaOptionId
      try {
        marcaOptionId = Integer.valueOf(marcaId['value'])
        if (EAV_MARCA_MAP[marcaOptionId]) {
          return  "${EAV_MARCA_MAP[marcaOptionId]} - "
        }
        else {
          return ""
        }
      }
      catch (NumberFormatException ex) { // eat it
      }
    }
    ""
  }


  static String decodeFotterColor(List sourceList, Map colorOption) {   // key:color  value:17
    Integer colorOptionId = 0
    try {
      colorOptionId = Integer.valueOf(colorOption['value'])
      return (getIdValueForColorValue(sourceList, EAV_COLOR_MAP[colorOptionId]) ?: "92028")
    }
    catch (NumberFormatException ex) { // eat it
    }

    "92028"
  }


  static String decodeFotterSize(List sourceList, String size) {
    if (!sourceList) {
      return null
    }
    else {
      return (getIdValueForSizeValue(sourceList, size) ?: "82073")
    }
  }


  /**
   * Find out from the category and the current colour if we can SKIP it

   * @param sourceList -  listOfPossibleColor id,name pairs
   * @param categoryId -   meliCategory
   * @param colorValue  -  '17'
   * @return true if colorValue is legit
   */
  static Boolean isColorValueSupported(List sourceList, String colorValue) {
    if (!sourceList) {
      return false
    }
    else {
      return (getIdValueForColorValue(sourceList, colorValue) != null ? true : false)
    }
  }

  static String getIdValueForColorValue(List sourceList, String colorName) {
    def attributeValues = sourceList?.find() {
      colorName?.equalsIgnoreCase(it.name)
    }
    return attributeValues?.id
  }

  static String getIdValueForSizeValue(List sourceList, String sizeName) {
    def attributeValues = sourceList?.find() {
      it.name == sizeName
    }
    return attributeValues?.id
  }


  // CONSTANTS
  def static EAV_COLOR_MAP =
    [120: "Amarillo",
        16: "Animal Print",
        13: "Azul",
        151: "Beige",
        11: "Blanco",
        355: "Bordo",
        129: "Cebra",
        84: "Celeste",
        144: "Coral",
        8: "Crudo",
        85: "Fucsia",
        14: "Gris",
        15: "Marron",
        86: "Multi",
        223: "Naranja",
        17: "Negro",
        10: "Oro",
        9: "Plata",
        18: "Rojo",
        12: "Rosa",
        366: "Suela",
        77: "Turquesa",
        39: "Verde",
        75: "Violeta",
        364: "Vison"]


  def static  EAV_MARCA_MAP =
    [344: "Abril Pereyra Lucena",
        46 : "Agua Patagona",
        357 : "Amsterdamn",
        339 : "Anne Bonny",
        396 : "Bendito Glam",
        237 : "Berna",
        244 : "Blackfin",
        250 : "Boating",
        125 : "Botas para Vos",
        338 : "CalleTanas",
        50 : "Cara y Seca",
        361 : "Carina Iribarren",
        354 : "Carla Danelli",
        353 : "Caro Caian",
        336 : "CAT",
        245 : "CataLacanna",
        156 : "Chueca",
        373 : "Chuela",
        249 : "Chypre",
        352 : "Clona",
        399 : "Corium",
        88 : "Corre Lola",
        351 : "Corre Lolita",
        126 : "Cover Your Bones",
        230 : "Crocs",
        340 : "Cul de Sac",
        209 : "De Maria",
        342 : "Demu",
        385 : "Diesel",
        231 : "Diez Indiecitos",
        371 : "Dos Almas",
        235 : "FB",
        122 : "Fotter",
        390 : "Foulee",
        45 : "Franco Pasotti",
        123 : "Frou Frou",
        229 : "Grendha",
        240 : "Gretaflora",
        239 : "Han Hol",
        143 : "Havaianas",
        350 : "Hermanos Estebecorena",
        238 : "Heyas",
        133 : "Hippo",
        119 : "Honkytonk",
        228 : "Ipanema",
        367 : "JOW",
        127 : "Juana Pascale",
        387 : "Juanita Jo",
        343 : "Julieta Sedler",
        255 : "Justa Osadia",
        251 : "Kappa",
        386 : "Kate Kuba",
        377 : "Kevingston",
        131 : "Krial",
        359 : "La Soberbia",
        389 : "Lady Stork",
        341 : "Laura Constanza",
        349 : "Lazaro",
        158 : "Le Loup",
        198 : "Levis",
        374 : "Lola Narsh",
        243 : "Lola Roca",
        150 : "Lomm",
        130 : "Los Dones",
        163 : "Lucerna",
        199 : "Lucila Iotti",
        345 : "Luria",
        392 : "Maggio Rossetto",
        246 : "Mandarine",
        48 : "Maria Josefa",
        142 : "Mary Joe",
        47 : "Mona",
        337 : "MUAA",
        164 : "Mule",
        157 : "Natacha",
        375 : "Naturezza",
        388 : "Nina Molina",
        347 : "Olivettas",
        49 : "Paez",
        369 : "Paris",
        241 : "Pepe Cantero",
        225 : "Perugia PEX",
        207 : "Pesqueira",
        398 : "Pigalle",
        44 : "Plum",
        348 : "Pony",
        208 : "Proforce",
        236 : "Rauch",
        226 : "Rauch + FB",
        89 : "Rave",
        253 : "Reef",
        254 : "Rip Curl",
        148 : "Seco",
        43 : "Shoeup",
        401 : "Simoneta Bags",
        159 : "Sofi Martire",
        252 : "Superga",
        360 : "Tessa",
        391 : "The Bag Belt"]
}
