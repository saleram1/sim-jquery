package com.mercadolibre.apps.sim.util;

import java.io.*;
import java.util.*;

import net.sf.flatpack.DataError;
import net.sf.flatpack.DataSet;
import net.sf.flatpack.DefaultParserFactory;
import net.sf.flatpack.Parser;
import net.sf.flatpack.ordering.OrderBy;
import net.sf.flatpack.ordering.OrderColumn;

/**
 * Utility class to turn Excel CSV buying_mode into a database or list of names
 * input files should be in buying_mode, as below, with Header row assumed
 * <p/>
 * <p/>
 * 1:email,first_name,last_name,prefix,phone,fax,title,company
 * 2:james.simmons@boeing.com,James H,Simmons,Mr,+1 112-445-6684,,AP Lead,Boeing
 * <p/>
 * TODO: support Gmail contact buying_mode and Apple vCard for Address Book import/export
 * <p/>
 * THIS CLASS TO BE REMOVED
 *
 * @see com.mercadolibre.apps.sim.CsvParserService
 */
public class CSVImporter {

  /**
   * Return a list of ContactBean for those contained in the file
   *
   * @param inputFile - full path to CSV or text file which must follow the example below
   * @return contactList - List of all the valid contacts
   * @throws Exception - IOException for example if the file is not found
   */
  public static List<Map> doImport(String inputFileName) throws Exception {
    ByteArrayInputStream pzmapXMLStream = new ByteArrayInputStream(TURBO_LISTER_FORMAT_STR.getBytes());
    FileInputStream dataSourceStream = new FileInputStream(new File(inputFileName));

    // delimited by a comma
    // text qualified by double quotes
    // ignore first record
    final Parser pzparser = DefaultParserFactory.getInstance().newDelimitedParser(pzmapXMLStream, dataSourceStream, ',', '\"', true);
    final DataSet ds = pzparser.parse();

    // re order the data set by company name
    OrderBy orderby = new OrderBy();
    orderby.addOrderColumn(new OrderColumn("category", false));
    ds.orderRows(orderby);

    List<Map> items = new Vector<Map>();
    int count = 0;

    while (ds.next()) {
      items.add(newItemMap(ds));
      count++;
    }

    if (ds.getErrors() != null && ds.getErrors().size() > 0) {
      System.err.println("FOUND ERRORS IN FILE");
    }
    System.out.println("Found " + count + " valid Items....");

    return items;
  }

  private static Map newItemMap(DataSet ds) {
    Map<String, Object> aMap = new HashMap<String, Object>();
    aMap.put("gp_id", ds.getString("gp_id"));
    aMap.put("buying_mode", ds.getString("format"));
    aMap.put("currency_id", ds.getString("currency"));
    aMap.put("title", ds.getString("title"));
    aMap.put("description", ds.getString("description"));
    aMap.put("category_id", ds.getString("category"));
    aMap.put("available_quantity", ds.getInt("available_quantity"));
    aMap.put("price", ds.getDouble("price"));
    aMap.put("condition", ds.getString("condition"));
    aMap.put("pictureURL", ds.getString("pictureURL"));  /// @TODO support multiple pictures - up to six

    // PICS up to six
    //
    // List pictures = new ArrayList()
    // for (int i = 0; i < 6; i++) {
    // 	if (ds.getString("pictureURL"+ String.valueOf(i))) {
    // 		pictures.add(ds.getString("pictureURL"+ String.valueOf(i)))
    // 	}
    // }
    //aMap.put("pictures", pictures)

    //https://api.mercadolibre.com/categories/MLA9997/listing_types
    if (null == ds.getString("listing_type")) {
      aMap.put("listing_type_id", "bronze");
    } else {
      aMap.put("listing_type_id", ds.getString("listing_type"));
    }

    return aMap;
  }


  static final String TURBO_LISTER_FORMAT_STR = "<?xml version='1.0'?>\n" +
      "<!DOCTYPE PZMAP SYSTEM " +
      "	\"flatpack.dtd\" >\n " +
      "<PZMAP>\n" +
      "	<COLUMN name=\"gp_id\" />" +
      "	<COLUMN name=\"format\" />" +
      "	<COLUMN name=\"currency\" />" +
      "	<COLUMN name=\"title\" />" +
      "	<COLUMN name=\"description\" />" +
      "	<COLUMN name=\"category\" />" +
      "	<COLUMN name=\"pictureURL\" />" +
      "	<COLUMN name=\"available_quantity\" />" +
      "	<COLUMN name=\"duration\" />" +
      "	<COLUMN name=\"price\" />" +
      "	<COLUMN name=\"shippingCosts\" />" +
      "	<COLUMN name=\"location\" />" +
      "	<COLUMN name=\"condition\" />" +
      "	<COLUMN name=\"shipsFrom\" />" +
      "	<COLUMN name=\"listing_type\" />" +
  "</PZMAP>\n";

}
