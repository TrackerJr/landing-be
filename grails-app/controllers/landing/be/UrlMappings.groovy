package landing.be

class UrlMappings {

    static mappings = {
        "/api/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'application', action:'index')
		"/download/$id"(controller: 'download', action: 'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
