package th.co.ais.landing

import grails.transaction.Transactional

import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Tag

@Transactional
class PageService {
	def grailsApplication
		
	def String getText(String path) {
		def folder = getTemplateFolder()
		
		return new File(folder, path)?.getText('UTF-8')
	}
	
	def boolean createFile(Page instance) {
		try {
			def folder = getTemplateFolder()
			
			def paths = instance.path.split("/")
			
			def dir = new File(folder, paths[0])
			if(!dir.exists()) {
				dir.mkdir()
			}
	
			Document doc = Jsoup.parse(instance.text)
			def head = doc.head()
			instance.links?.each {
				head.append("""<link rel="stylesheet" type="text/css" href="${it.link}">""".toString())
			}
			
			new File(dir, 'bin.html').withWriter('UTF-8') {
				it.writeLine doc.outerHtml()
			}
			
			new File(dir, paths[1]).withWriter('UTF-8') {
				it.writeLine instance.text
			}
			return true
		} catch (e) {
			log.error "create file error: ", e
			return false
		}
	}
	
	def deleteFile(Page instance) {
		log.debug "Delete directory: ${instance.path}"
		def folder = getTemplateFolder()
		def template = instance.path.split("/")
		def file = new File(folder, template[0])
		
		if(file.exists()) {
			FileUtils.deleteDirectory(file)
		}
	}
	
	private File getTemplateFolder() {
		def upload = new File(grailsApplication.config.grails.plugins.fileserver.paths.template)
		if(!upload.exists()) {
			upload.mkdir()
		}
		upload
	}
	
	/*private takeScreenShot(File dir) {
		File index = new File(dir, 'index.html')
		Desktop.getDesktop().browse(index.toURI());
		BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(image, "png", new File("screenshot.png"));
	}*/
    
}
