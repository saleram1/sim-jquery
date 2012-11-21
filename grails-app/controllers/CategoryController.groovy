
import com.mercadolibre.apps.sim.data.bo.imports.meli.Category
import grails.converters.JSON


class CategoryController {

	def search() {
    def matchingCategories = []

    if (params.term) {
      matchingCategories = Category.findAllByMeliIdLike(params.term + "%").collect { Category it ->
        "${it.meliId} - ${it.fqn}"
      }
    }

    render matchingCategories as JSON
//		render categoryList.findAll() { it.toUpperCase().startsWith(params.term) } as JSON
	}
}
