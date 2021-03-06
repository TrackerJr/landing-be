package th.co.ais.landing

import grails.rest.Resource

@Resource(uri='/api/page', formats=['json', 'xml'], superClass=PageRestfulController)
class Page {
	static transients = ['text']
	
	String name
	String path
	String text
	List<Links> links
	
	static hasMany = [links: Links]
	
    static constraints = {
    }
}
