package th.co.ais.landing

import grails.transaction.Transactional

@Transactional
class UserService {
	def grailsApplication

	def init() {
		def users = grailsApplication.config.users

		users?.each {
			createUserRole(
					createUser(it.username, it.password),
					createRole(it.authority)
					)
		}

		assert User.count() >= 1
		assert Role.count() >= 1
		assert UserRole.count() >= 1
	}

	def User createUser(String username, String password) {
		User user = User.findByUsername(username)
		if(!user) {
			user = new User(username: username, password: password)
			user.save(failOnError: true)
		}
		return user
	}

	def Role createRole(String authority) {
		Role role = Role.findByAuthority(authority)
		if(!role) {
			role = new Role(authority: authority);
			role.save(failOnError: true)
		}
		return role
	}

	def createUserRole(User user, Role role) {
		UserRole userRole = UserRole.findByUserAndRole(user, role)
		if(!userRole) {
			UserRole.create(user, role, true)
		}
	}
	
	def getRoleCount() {
		Role.count()
	}
	
	def getUserCount() {
		User.count()
	}
}
