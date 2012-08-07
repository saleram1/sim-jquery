class UrlMappings {

	static mappings = {
/*		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
*/

		"/authorize"(controller: "itemImportFileSource", action: "authorize")
		"/itemImport/$action/$id?"(controller: "itemImport")
		"/itemImportFileSource/save"(controller: "itemImportFileSource", action: "save")
		"/uploads/new"(controller: "itemImportFileSource", action: "create")
		"/uploads/next"(controller: "itemImport", action: "showUploadProgress")

		"/"(view:"/index")
		"500"(view:'/error')
	}
}