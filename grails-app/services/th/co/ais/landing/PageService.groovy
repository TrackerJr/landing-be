package th.co.ais.landing

import grails.transaction.Transactional

@Transactional
class PageService {

	def createFile(String parthStr, Page instance) {
		def dir = new File(parthStr)
		if(!dir.exists()) {
			dir.mkdir()
		}
		new File(instance.path).withWriter('UTF-8') {
			it.writeLine instance.text
		}
	}
    
}
