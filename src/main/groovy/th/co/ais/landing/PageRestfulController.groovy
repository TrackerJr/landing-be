package th.co.ais.landing

import static org.springframework.http.HttpStatus.*
import grails.rest.RestfulController
import grails.transaction.Transactional
import grails.web.http.HttpHeaders

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class PageRestfulController<T> extends RestfulController<T>{
	def pageService
	
	PageRestfulController(Class<T> resource) {
		this(resource, false)
	}

	PageRestfulController(Class<T> resource, boolean readOnly) {
		super(resource, readOnly)
	}
	
	/**
	 * Saves a resource
	 */
	@Transactional
	def save() {		
		if(handleReadOnly()) {
            return
        }
		def pathStr = "/file/T${(new Date().getTime()).toString()}"
		
		String text = request.JSON.text
		Document doc = Jsoup.parse(text)		
		
        def instance = resource.newInstance()
		instance.name = doc.title() ?: 'Unknown'
		instance.path = "$pathStr/index.html"
		instance.text = text

        instance.validate()
        if (instance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond instance.errors, view:'create' // STATUS CODE 422
            return
        }
		pageService.createFile(pathStr, instance)
        saveResource instance

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: "${resourceName}.label".toString(), default: resourceClassName), instance.id])
                redirect instance
            }
            '*' {
                response.addHeader(HttpHeaders.LOCATION,
                        grailsLinkGenerator.link( resource: this.controllerName, action: 'show',id: instance.id, absolute: true,
                                            namespace: hasProperty('namespace') ? this.namespace : null ))
                respond instance, [status: CREATED]
            }
        }
	}
}
