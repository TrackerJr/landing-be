package th.co.ais.landing

import static org.springframework.http.HttpStatus.*
import grails.rest.RestfulController
import grails.transaction.Transactional
import grails.web.http.HttpHeaders

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.http.HttpStatus;

class PageRestfulController<T> extends RestfulController<T>{
	def pageService
	//def grailsApplication
	
	PageRestfulController(Class<T> resource) {
		this(resource, false)
	}

	PageRestfulController(Class<T> resource, boolean readOnly) {
		super(resource, readOnly)
	}
	
	@Override
	def show() {		
		def page = queryForResource(params.id)
		
		respond ([id: page.id, name: page.name, path: page.path, text: pageService.getText(page.path)])
	}
	
	/**
	 * Saves a resource
	 */
	@Override
	@Transactional
	def save() {		
		if(handleReadOnly()) {
            return
        }
		
		Document doc = getDocument()		
        def instance = resource.newInstance()
		instance.name = doc.title() ?: 'Unknown'
		instance.path = "T${(new Date().getTime()).toString()}/index.html"
		instance.text = request.JSON.text

        instance.validate()
        if (instance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond instance.errors, view:'create' // STATUS CODE 422
            return
        }
		pageService.createFile(instance)
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
	
	/**
	 * Updates a resource for the given id
	 * @param id
	 */
	@Override
	@Transactional
	def update() {
		if(handleReadOnly()) {
			return
		}

		Document doc = getDocument()
		def instance = resource.get(params.id)
		instance.name = doc.title() ?: 'Unknown'
		instance.text = request.JSON.text
		
		
		if (instance == null) {
			transactionStatus.setRollbackOnly()
			notFound()
			return
		}
		
		if (instance.hasErrors()) {
			transactionStatus.setRollbackOnly()
			respond instance.errors, view:'edit' // STATUS CODE 422
			return
		}
		
		pageService.createFile(instance)
		updateResource instance
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [message(code: "${resourceClassName}.label".toString(), default: resourceClassName), instance.id])
				redirect instance
			}
			'*'{
				response.addHeader(HttpHeaders.LOCATION,
						grailsLinkGenerator.link( resource: this.controllerName, action: 'show',id: instance.id, absolute: true,
											namespace: hasProperty('namespace') ? this.namespace : null ))
				respond instance, [status: OK]
			}
		}
	}

	/**
	 * Deletes a resource for the given id
	 * @param id The id
	 */
	@Override
	@Transactional
	def delete() {
		if(handleReadOnly()) {
			return
		}

		def instance = queryForResource(params.id)
		if (instance == null) {
			transactionStatus.setRollbackOnly()
			notFound()
			return
		}

		pageService.deleteFile(instance)
		instance.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [message(code: "${resourceClassName}.label".toString(), default: resourceClassName), instance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT } // NO CONTENT STATUS CODE
		}
	}
	
	
	private getDocument() {
		String text = request.JSON.text
		return Jsoup.parse(text)
	}
}
