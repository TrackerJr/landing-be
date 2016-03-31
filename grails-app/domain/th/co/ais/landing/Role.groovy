package th.co.ais.landing

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
@Resource(uri='/api/role', formats=['json', 'xml'])
class Role implements Serializable {

	private static final long serialVersionUID = 1

	String authority

	Role(String authority) {
		this()
		this.authority = authority
	}

	static constraints = {
		authority blank: false, unique: true
	}

	static mapping = {
		cache true
	}
}
