class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		"/upload/new"(controller: "itemImportFileSource", action: "create")

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
