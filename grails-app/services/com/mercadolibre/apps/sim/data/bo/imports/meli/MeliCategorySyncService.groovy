package com.mercadolibre.apps.sim.data.bo.imports.meli

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import net.sf.json.JSONArray

    
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

import java.util.zip.*

class MeliCategorySyncService {

  def syncCategoryZipFile() {
    // TODO:  put this URL in config
//    downloadCategoryZipFileOLD2("https://api.mercadolibre.com/sites/MLA/categories/all")
//    downloadCategoryZipFile("https://api.mercadolibre.com/sites/MLA/categories/all")
//    downloadCategoryZipFile9("https://api.mercadolibre.com/sites/MLA/categories/all")
    downloadCategoryZipFileFromMeli("https://api.mercadolibre.com/sites/MLA/categories/all")
  }
  
  
  def isMeliCategoryInSync() {
    return false 
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
}
