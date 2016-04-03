package th.co.ais.landing

import grails.rest.Resource

@Resource(uri='/api/page', formats=['json', 'xml'], superClass=PageRestfulController)
class Page {
	static transients = ['text']
	
	String name
	String path
	String text
	
    static constraints = {
    }
	
	def getText() {
		return this.text
	}
	
	def setText(String text) {
		this.text = text;
	}
}
