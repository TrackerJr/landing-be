package th.co.ais.landing

import grails.rest.Resource

@Resource(uri='/api/fileItem', formats=['json', 'xml'])
class FileItem {

	String name
	
	static belongsTo = FolderItem
    static constraints = {
    }
}
