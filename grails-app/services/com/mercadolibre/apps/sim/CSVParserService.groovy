package com.mercadolibre.apps.sim

import org.grails.plugins.csv.CSVMapReader


class CsvParserService {

  static transactional = false

  /**
   * Make this more robust by using the OpenCSV (latest) library which handles descriptions with embedded quotes and
   * so forth
   *
   * @return List<Map>  one per row
   */
  List parseItemInventoryFile(File inputFile) {
    def rowList = []

    // create Reader if the file contents are *more than just the header* line
    if (inputFile?.exists() && inputFile.length() > MIN_LENGTH) {
      FileReader reader = new FileReader(inputFile)

      //assumes the first line of the file has the field names
      new CSVMapReader(reader).each { map ->
        rowList.add(map)
      }

      reader.close()
    }
    return rowList
  }

  static final Integer MIN_LENGTH = 142
}
