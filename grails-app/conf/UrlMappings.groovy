class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/graphQL"() {
            controller = "graphQL"
            //action = [POST: "query"]
            action = "query"
        }

        "/games"() {
            controller = "game"
            //action = [POST: "query"]
            action = "query"
        }

        "/"(view:"/index")
        "500" (controller: "error", action: "internalError")
	}
}
