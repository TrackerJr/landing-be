package th.co.ais.landing


import grails.converters.*
import grails.rest.*

class DashboardController {
	def dashboardService
    def index() { 
		
		def datas = dashboardService.getChartInfo()
		
		render datas as JSON
	}
}
