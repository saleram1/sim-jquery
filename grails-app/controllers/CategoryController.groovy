
import grails.converters.JSON

class CategoryController {
	
	def static categoryList = ["MLA9997", "MLA78884", "MLA44393", "MLA1458"]
	
	
	def search() {
		render categoryList.findAll() { it.toUpperCase().startsWith(params.term) } as JSON
	}
}
