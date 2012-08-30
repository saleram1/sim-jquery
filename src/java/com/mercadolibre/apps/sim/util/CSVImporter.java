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
* Utility class to turn Excel CSV format into a database or list of names
* input files should be in format, as below, with Header row assumed
*
* 
* 1:email,first_name,last_name,prefix,phone,fax,title,company
* 2:james.simmons@boeing.com,James H,Simmons,Mr,+1 112-445-6684,,AP Lead,Boeing
*
* TODO: support Gmail contact format and Apple vCard for Address Book import/export
*/
public class CSVImporter {

	/**
	* Return a list of ContactBean for those contained in the file
	*
	* @param inputFile - full path to CSV or text file which must follow the example below
	* @return contactList - List of all the valid contacts
	* @throws Exception  - IOException for example if the file is not found
	*/
	public static List<Map> doImport( String inputFileName ) throws Exception {
		ByteArrayInputStream pzmapXMLStream = new ByteArrayInputStream( TURBO_LISTER_FORMAT_STR.getBytes() );
		FileInputStream dataSourceStream = new FileInputStream( new File( inputFileName ) );

		// delimited by a comma
		// text qualified by double quotes
		// ignore first record 
		final Parser pzparser = DefaultParserFactory.getInstance().newDelimitedParser( pzmapXMLStream, dataSourceStream, ',', '\"', true);
		final DataSet ds = pzparser.parse();

		// re order the data set by company name
		OrderBy orderby = new OrderBy();
		orderby.addOrderColumn(new OrderColumn("category", false));
		ds.orderRows(orderby);

		List<Map> items = new Vector<Map>();
		while (ds.next()) {
			items.add( newItemMap(ds) );
		}

		if (ds.getErrors() != null && ds.getErrors().size() > 0) {
			System.err.println("FOUND ERRORS IN FILE");
		}
		return items;
	}
	
	private static Map newItemMap( DataSet ds ) {
		Map<String,Object> aMap = new HashMap<String,Object>();
		aMap.put("gp_id", ds.getString("gp_id") );
		aMap.put("site_id", ds.getString("site") );				/// no longer required
		aMap.put("buying_mode", ds.getString("format") );
		aMap.put("currency_id", ds.getString("currency") );
		aMap.put("title", ds.getString("title") );
		aMap.put("description", ds.getString("description") );
		aMap.put("category_id", ds.getString("category") );
		aMap.put("available_quantity", ds.getInt("available_quantity") );
		aMap.put("duration", ds.getInt("duration") );
		aMap.put("price", ds.getDouble("price") );
		aMap.put("shippingCosts", ds.getDouble("shippingCosts") );
		aMap.put("location", ds.getString("location") );
		aMap.put("condition", ds.getString("condition") );
		aMap.put("shipsFrom", ds.getString("shipsFrom") );

		aMap.put("pictureURL", ds.getString("pictureURL") );	/// @TODO support multiple pictures - up to six
		// 
		// List pictures = new ArrayList()
		// for (int i = 0; i < 6; i++) {
		// 	if (ds.getString("pictureURL"+ String.valueOf(i))) {
		// 		pictures.add(ds.getString("pictureURL"+ String.valueOf(i)))
		// 	}
		// }
		//aMap.put("pictures", pictures)		

		return aMap;
	}


	static final String TURBO_LISTER_FORMAT_STR = "<?xml version='1.0'?>\n" +
		"<!DOCTYPE PZMAP SYSTEM " +
		"	\"flatpack.dtd\" >\n " +
		"<PZMAP>\n" +
		"	<COLUMN name=\"gp_id\" />" +
		"	<COLUMN name=\"site\" />" +
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
		"</PZMAP>\n";
		
}
