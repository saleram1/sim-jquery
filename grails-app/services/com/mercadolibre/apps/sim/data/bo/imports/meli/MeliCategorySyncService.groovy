package com.mercadolibre.apps.sim.data.bo.imports.meli

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import net.sf.json.JSONArray

import groovyx.net.http.RESTClient
import groovy.util.slurpersupport.GPathResult
import static groovyx.net.http.ContentType.URLENC 
    
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

import java.util.zip.*

import com.mercadolibre.apps.sim.data.bo.imports.meli.CategoryVersion

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.GZIPInputStream

class MeliCategorySyncService {

  def syncCategoryZipFile() {
    // TODO:  put this URL in config
    downloadCategoryZipFileFromMeli("https://api.mercadolibre.com/sites/MLA/categories/all")
  }
  
  def isMeliCategoryInSync() {
	
    CategoryVersion categoryVersion = CategoryVersion.get(1)
	println "This is the categoryVersion from the lookup: " + categoryVersion
	if (categoryVersion.md5 == getMeliCategoryMD5()) {
      return true
	} 
    return false 
  }

  def getMeliCategoryMD5() {
	String md5
	RESTClient meliRestClient = new RESTClient( 'https://api.mercadolibre.com/sites/MLA/categories/' )

	Object blah = meliRestClient.head( path : 'all' )

	blah.getHeaders().each { 
	  String meliHeader = it
	  if (meliHeader.substring(0,13).equals("X-Content-MD5")) {
		md5 = meliHeader.substring(14).trim()
	  }
	}
	md5
  }

  def saveMeliCategoryMD5() {
	/*RESTClient meliRestClient = new RESTClient( 'https://api.mercadolibre.com/sites/MLA/categories/' )

		Object blah = meliRestClient.head( path : 'all' )

		blah.getHeaders().each { 
		  String meliHeader = it
		  if (meliHeader.substring(0,13).equals("X-Content-MD5")) {
			// save X-Content-MD5 value to the database - CategoryVersion domain object
			String md5 = meliHeader.substring(14).trim()
			//println "md5: " + md5
			CategoryVersion categoryVersion = new CategoryVersion()
			categoryVersion.md5 = md5*/
		
		CategoryVersion categoryVersion = new CategoryVersion()
		categoryVersion.md5 = getMeliCategoryMD5()
		println "Here is the md5: " + categoryVersion.md5	
		categoryVersion.save(flush: true)
		//println it
	  /*}
	  	}*/
  }
  
//  private downloadCategoryZipFileOLD2(address)
//  {
//    def file = new FileOutputStream("/tmp/MeliCategoryZipFile.gz")
//    def output = file
//    URL url = new URL(address)
//    InputStream input = url.newInputStream()
//    
//    byte[] buffer = new byte[1024];
//
//        try {
//            println "Downloading file..."
//            for (int length; (length = input.read(buffer)) != -1;) {
//                output.write(buffer, 0, length);
//            }
//          println "Successfully downloaded"
//        } finally {
//            if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
//            if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
//        }
//
//    //out << new URL(address).openStream()
////    out.flush()
////    out.close()
//  }
 
//  private downloadCategoryZipFile(url) {
//    
//    def http = new HTTPBuilder("https://api.mercadolibre.com")
//
//    def file = new FileOutputStream("/tmp/MeliCategoryZipFile2.gz")
//    def output = new BufferedOutputStream(file)
//    
//    try {
//      http.get( uri: "/sites/MLA/categories/all", contentType : ContentType.TEXT) { resp, zip ->
////         System.out << zip
//             output << zip
//
//      }
//    } catch (Exception e) {
//      println "The file is not accessible:  ${e.message}"
//      e.printStackTrace()
//    } finally {
//      output.flush()
//      output.close()
//    }  
//  }
//  
//  private downloadCategoryZipFile9(address) {
// 
//    URL url = new URL(address)
//    InputStream inputStream = url.newInputStream()
//    
//    def file = new FileOutputStream("/tmp/MeliCategoryZipFile3.gz")
//    def output = new BufferedOutputStream(file)
//    
//    // Create a GZIPInputStream...
//    input = new GZIPInputStream(inputStream)
//    // And a reader so we get the nice readLine...
//    reader = new BufferedReader(new InputStreamReader(input))
//
//    // Read the special first line...column headings say. 
//    //firstLine = reader.readLine()
//    //println "firstLine: $firstLine"
//
//    reader.readLine()
//    // Read the rest of the file...reader picks up where 
//    // it was left off with eachLine
//    reader.eachLine{line->
//      output << line
//    }
//
//  }
  
  private downloadCategoryZipFileFromMeli(url) {
    
    HttpClient client = new DefaultHttpClient()
    HttpGet get = new HttpGet("https://api.mercadolibre.com/sites/MLA/categories/all")
    HttpResponse response = client.execute(get)
    
    InputStream input = null
    OutputStream output = null
    byte[] buffer = new byte[1024]

    try {
      input = response.getEntity().getContent()
        output = new FileOutputStream("/tmp/mercadoCatFile.gz")
        for (int length; (length = input.read(buffer)) > 0;) {
            output.write(buffer, 0, length)
        }
    } finally {
        if (output != null) try { output.close() } catch (IOException logOrIgnore) {}
        if (input != null) try { input.close() } catch (IOException logOrIgnore) {}
    }
  }

  private static final String INPUT_GZIP_FILE = "/tmp/myCatFile2.gz"
  private static final String OUTPUT_FILE = "/tmp/myCatFile2.txt"

  def unzipCategoryZipFileFromMeli(String locationOfFile) {
	
	byte[] buffer = new byte[1024];

     try{

    	 GZIPInputStream gzis = 
    		new GZIPInputStream(new FileInputStream(INPUT_GZIP_FILE));

    	 FileOutputStream out = 
            new FileOutputStream(OUTPUT_FILE);

        int len;
        while ((len = gzis.read(buffer)) > 0) {
        	out.write(buffer, 0, len);
        }

        gzis.close();
    	out.close();

    	System.out.println("Done");

    }catch(IOException ex){
       ex.printStackTrace();   
    }
  }
}