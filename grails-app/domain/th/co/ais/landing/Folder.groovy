package th.co.ais.landing

import grails.rest.Resource

@Resource(uri='/api/folder', formats=['json', 'xml'])
class Folder {

	String name
	//String path
	Folder parent
	List<Folder> children
	List<File> files
	
	static hasMany = [children: Folder, files: File]
	
    static constraints = {
    }
}
