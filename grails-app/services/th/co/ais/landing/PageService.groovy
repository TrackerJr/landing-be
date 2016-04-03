package th.co.ais.landing

import grails.transaction.Transactional

import java.awt.Desktop
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import org.apache.commons.io.FileUtils

@Transactional
class PageService {
	def grailsApplication
	
	def String getText(String path) {
		def folder = getTemplateFolder()
		
		return new File(folder, path)?.getText('UTF-8')
	}
	
	def File createFile(Page instance) {
		def folder = getTemplateFolder()
		
		def paths = instance.path.split("/")
		
		def dir = new File(folder, paths[0])
		if(!dir.exists()) {
			dir.mkdir()
		}
		
		new File(dir, paths[1]).withWriter('UTF-8') {
			it.writeLine instance.text
		}
		/*try {
			takeScreenShot(dir)
		} catch(e){}*/
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
		
		def folder = new File(upload, grailsApplication.config.grails.plugins.fileserver.paths.prefix)
		if(!folder.exists()) {
			folder.mkdir()
		} 
		folder
	}
	
	private takeScreenShot(File dir) {
		File index = new File(dir, 'index.html')
		Desktop.getDesktop().browse(index.toURI());
		BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(image, "png", new File("screenshot.png"));
	}
    
}
