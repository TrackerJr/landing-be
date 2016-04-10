package th.co.ais.landing


import grails.rest.*
import grails.converters.*

class DownloadController {
	def pageService
    def index() { 
		
		def page = Page.get(params.id)
		def basePath = grailsApplication.config.grails?.plugins?.fileserver?.paths?.get('template')
		println "basePath: $basePath"
		println "path: ${page.path}"
		File file = basePath ? pageService.loadFile(basePath, page.path) : null
		
		println file?.text //T1460261697983/index.html
		
		/*println params.id
		def root = 'template'
		def path = getPath(request.forwardURI, params.id)

		def basePath = grailsApplication.config.grails?.plugins?.fileserver?.paths?.get(root)
		
		println "path: $path"
		println "basePath: $basePath"
		File file = basePath ? pageService.loadFile(basePath, path) : null
		
		if (file) {
			log.debug("$root/$path, sending file: $file.absolutePath")
			response.setContentType("application/octet-stream")
			response.setHeader("Content-disposition", "filename=${file.getName()}")
			response.setHeader("Content-Type","application/force-download");
			response.outputStream << file.newInputStream()
		} else {
			log.debug("$root/$path, file not found - dir: $basePath, file: $path")
			response.status = 404
		}*/
	}
	
	private String getPath(uri, path) {
		String last = uri.substring(uri.lastIndexOf('/') + 1)
		String path2 = path.substring(0, path.lastIndexOf('/') + 1)
		return path2 + last
	}
}
