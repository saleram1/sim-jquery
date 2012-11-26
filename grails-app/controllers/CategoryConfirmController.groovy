import com.mercadolibre.apps.sim.CategoryService
import grails.converters.JSON


/**
 * Service to check for Fashion or NON-fashion category primarily called from front end
 *
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/16/12
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
class CategoryConfirmController {

  CategoryService categoryService


  def index(CategoryConfirmCommand command) {

    log.info "Confirming for User category: ${command.category}"

    def validCategory
    if (categoryService.isValidCategory(command.siteCategoryId)) {
      validCategory = new CategoryConfirmResponse(id: command.siteCategoryId, name: "", isFashion: Boolean.FALSE)

      if (categoryService.isFunkyFashionFootwearCategory(command.category)) {
        validCategory.isFashion = Boolean.TRUE
      }
    }
    else {
      validCategory = JSON.parse(categoryService.getCategoryNotFoundMessage(command.siteCategoryId))
    }

    render validCategory as JSON
  }
}


class CategoryConfirmCommand {
  String category

  String getSiteCategoryId() {
    if (this.category.contains("-")) {
      this.category.substring(0, this.category.indexOf("-")).trim()
    }
    else {
      this.category.trim()
    }
  }
}


class CategoryConfirmResponse {
  String id
  String categoryType = "MELI"
  String name = "coming soon"
  Boolean isFashion = false
}
