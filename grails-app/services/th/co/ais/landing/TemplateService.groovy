package th.co.ais.landing

import grails.transaction.Transactional
import groovy.io.FileType

@Transactional
class TemplateService {

    def getTemplate() {
		
		/*new File("/upload/default").eachFileRecurse(FileType.FILES) {
			println it.name
		}*/
				
	}
}
