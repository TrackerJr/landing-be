package th.co.ais.landing

import grails.rest.Resource

@Resource(uri='/api/folderItem', formats=['json', 'xml'])
class FolderItem {

	String name
	String path
	FolderItem folder
	List<FolderItem> folders
	List<FileItem> files
	
	static hasMany = [folders: FolderItem, files: FileItem]
    
	static constraints = {
    }
	
}
