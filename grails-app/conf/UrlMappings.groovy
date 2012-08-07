class UrlMappings {

	static mappings = {
/*		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
*/

		"/itemImport/$action/$id?"(controller: "itemImport")
		"/itemImportFileSource/save"(controller: "itemImportFileSource", action: "save")
		"/uploads/new"(controller: "itemImportFileSource", action: "create")
		"/uploads/next"(controller: "itemImport", action: "showUploadProgress")
		"/access"(controller: "access", action: "index")
		
		"/"(view:"/index")
		"500"(view:'/error')
	}
}