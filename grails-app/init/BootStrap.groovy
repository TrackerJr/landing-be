class BootStrap {
	
	def userService
	
    def init = { servletContext ->
		userService.init()
    }
    def destroy = {
    }
}
