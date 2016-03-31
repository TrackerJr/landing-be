package th.co.ais.landing

import grails.transaction.Transactional
import groovy.io.FileType;

@Transactional
class FolderService {
	def grailsApplication
	
	def getFolders() {		
		def dir = new File('/files')
		if(!dir.exists()) {
			dir.mkdir();
		}
		def folders = []
		dir.eachFile(FileType.DIRECTORIES) {
			log.debug "Folder : ${it.name}"
			folders << [name: it.getName(), path: it.getPath(), parent: '/']
		}
		return folders
	}
	
	def createFolder(String name, String parent) {
		def dir = new File("/files/$name")
		if(!dir.exists()) {
			dir.mkdir()
		}
		[name: dir.getName(), path: dir.getPath(), parent: '/']
	}
    
}
