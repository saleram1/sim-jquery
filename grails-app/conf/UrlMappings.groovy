class UrlMappings {

	static mappings = {
/*		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
*/
		"/uploads/new"(controller: "itemImportFileSource", action: "create")
		"/uploads/next"(controller: "itemImport", action: "showUploadProgress")
		"/itemImport/$action/$id?"(controller: "itemImport")
		"/itemImportFileSource/save"(controller: "itemImportFileSource", action: "save")
		
		"/"(view:"/index")
		"500"(view:'/error')
	}
}