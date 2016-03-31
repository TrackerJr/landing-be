package th.co.ais.landing


import grails.converters.*
import grails.rest.*

class FolderController {
	def folderService
	
    def index() {
		log.debug "Request : ${request.method}"
		switch(request.method) {
			case 'GET':
				_get();
				break;
			case 'POST':
				_post();
				break;
		}
	}
	
	private _get() {
		def folders = folderService.getFolders()
		render (folders as JSON)
	}
	
	private _post() {
		def folder = folderService.createFolder(request.JSON.name, request.JSON.parent)
		render (folder as JSON)
	}
}
