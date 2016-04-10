package th.co.ais.landing

class Links {
	enum Type {
		StyleSheet, JavaScript
	}
	
	Type type
	String link
	
	static belongsTo = [page: Page]
	
    static constraints = {
    }
}
