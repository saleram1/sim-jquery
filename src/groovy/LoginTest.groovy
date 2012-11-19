
import groovyx.net.ws.*;
import groovyx.net.ws.cxf.SoapVersion;


class LoginTest {
	def static mage_url = 'http://ec2-107-22-49-30.compute-1.amazonaws.com/magento/index.php/api/v2_soap?wsdl=1';
	def static mage_user = 'm2m'; 
	def static mage_api_key = '2dxkp14lggreh7f5m8ejqmzyskwhzoj0';


	def startLogin() {
		def proxy = new WSClient( "http://ec2-107-22-49-30.compute-1.amazonaws.com/magento/index.php/api/v2_soap?wsdl=1",
			Thread.currentThread().getContextClassLoader() )
		proxy.setPreferredSoapVersion(SoapVersion.SOAP_1_1)
		proxy.initialize()

		def session
		
		try {
		  session = proxy.login("m2m", "2dxkp14lggreh7f5m8ejqmzyskwhzoj0")
		  println "SessionID: ${session}"
		
		  proxy.directoryCountryList( session ).each {
			println it
 		}
		  println proxy.catalogCategoryAssignedProducts(session, 5, "");
		
		}	catch (Throwable tr) {
		  println tr.message
		}

	}
}
