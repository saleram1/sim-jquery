class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/authorize"(controller: "itemImportFileSource", action: "authorize")
		"/help"(controller: "help", action: "index")
		"/itemImportFileSource/save"(controller: "itemImportFileSource", action: "save")
		"/itemImport/$action/$id?"(controller: "itemImport")
		"/itemz/callback"(controller: "itemsCallback", action: "index")
		"/signups/new"(controller: "signup", action: "create")
		"/users/show/$id?"(controller: "user", action: "show")
		"/uploads/new"(controller: "itemImportFileSource", action: "create")
		"/uploads/next"(controller: "itemImport", action: "showUploadProgress")
		"/logout"(controller: "signout", action: "index")
		
		"/"(view:"/index")
		"500"(view:'/error')
	}
}