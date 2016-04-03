package th.co.ais.landing

import grails.transaction.Transactional

@Transactional
class DashboardService {
	
	def userService
	
	def getChartInfo() {
		[
			getPageInfo(),
			getUserInfo()			
		]
	}

    def getUserInfo() {
		[
			title: 'User info',
			chartType: 'Doughnut', 
			labels: ['User', 'Role'], 
			data: [
				userService.getUserCount(),
				userService.getRoleCount()
			]
		]
	}
	
	def getPageInfo() {
		[
			title: 'Page info',
			chartType: 'Bar',
			labels: ['2015', '2016', '2017'],
			data: [
				[65, 59],
				[28, 48]
			]
		]
	}
}
