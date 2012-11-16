import com.mercadolibre.apps.sim.CategoryService
import grails.converters.JSON


/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/16/12
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
class CategoryConfirmController {

  CategoryService categoryService


  def index(CategoryConfirmCommand command) {

    def validCategory

    if (categoryService.isValidCategory(command.category)) {
      // we have to make another api call or DB query to get the name atm

      validCategory = new CategoryConfirmResponse(id: command.category, name: "", isFashion: "false")

      if (categoryService.isFunkyFashionFootwearCategory(command.category)) {
        validCategory.isFashion = "true"
      }
    }

    render validCategory as JSON
  }
}


class CategoryConfirmCommand {
  String category
}

/*
  "id": "MLA9997",
  "name": "Botas de Media Caña",
  "permalink": null,
  "total_items_in_this_category": 2053,
  "path_from_root": [...],
  "children_categories": [
  ],
  "settings": {...},
}
 */
class CategoryConfirmResponse {
  String id
  String categoryType = "MELI"
  String name = "coming really soon"
  String isFashion = false
}
