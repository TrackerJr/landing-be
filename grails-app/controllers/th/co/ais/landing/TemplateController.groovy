package th.co.ais.landing


import grails.converters.*
import grails.rest.*

class TemplateController {

	def templateService
	
    def index() {
		def data = templateService.getTemplate()
		render(data as JSON)
	}
}