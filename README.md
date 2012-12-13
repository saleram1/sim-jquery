// A branch representing the state of sim-jquery prior to domain purge of 
// currently unnecessary objects

Simple Inventory Management (SIM)
---------------------------------

A Tool for ML Power Sellers
SIM is the user-facing and administration component of the Item / listing batch processor.  
 

Among other things, SIM offers the following feature set:

Sellers will send product data formatted to the Marketplace specifications.  Initially this will be CSV only, followed by [tbd](https://developers.google.com/google-apps/spreadsheets/)  The following integration specifics should also agreed upon with the Marketplace.

This operation is effectively a 'synch' or a merge of all items coming from the batch, with all items that exist presently in the marketplace.  It is assuming that the SIM middleware keeps a table mapping gp_id/item_id relationship.

Each batch (say, max. of 1000 items) will be stamped with a sellerId;
Every item will be unique, identified by the gp_id , essentially ChannelAdvisor's SKU

Each item record must contain these attributes at minimum: title, description, category_id*, price, currency_iso*, available_quantity, listing_type_id*, condition* - full list of supported fields: gp_id,site,

For each batch of Items in file, Seller expects to receive an error report detailing the failure of any item sent in the Product Feed.  Errors may include:
1. Missing required product data
2. Invalid product data / user data

Example Validation:
	"The attributes [gp_id] are invalid for category MLA3530"  <-- attributes are stored per category
	"Item pictures are mandatory for listing type gold"    <-- listing is gold but pictureURL is left blank
	"Property [initial_quantity] with value [0] is less than minimum value [1]"  <-- general validation  
	such fields as price, quantity must be non-zero  certain Item props are required for listing

format,currency,title,description,category,pictureURL,quantity_available,duration,price,shippingCosts,location,condition,shipsFrom

Details <a id="discussion-list" />
-------

More about our project is available on basecamp [here](https://basecamp.com/1785050/projects/916773-sim)

This version of SIM is using the jQuery file upload plug


Installation and Requirements <a id="install" />
-----------------------------

SIM from a developer client requires Grails 1.3.7 or above (latest at time of this writing was 2.0.4)

Once you have a fork of this repo, should be able to run the following :

* grails test-app unit:
* grails test-app integration:
* grails run-app
* grails prod war



Configuration  <a id="configuration"></a>
-------------

TBD


Acknowledgements <a id="acknowledgements" />
----------------

Thanks to the items-api core team in Buenos Aires, plus the team in Palo Alto - without whom:

  * Rodri
  * Vasanth
  * Salera
