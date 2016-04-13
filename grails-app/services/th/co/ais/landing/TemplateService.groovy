package th.co.ais.landing

import grails.transaction.Transactional
import groovy.io.FileType

@Transactional
class TemplateService {

    def getTemplate() {
		def list = []
		new File('template')?.eachFileRecurse(FileType.FILES) {
			list << [title: it.getName()-'.html', content: it.text]
		}
		return list
	}
}
