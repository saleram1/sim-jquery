class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		"/uploads/new"(controller: "itemImportFileSource", action: "create")
		"/uploads/show/latest"(controller: "itemImport", action: "showUploadProgress")

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
