package fr.isen.attia.androidrestaurant.food

import java.io.Serializable

/**
 * Data classes needed for the JSON conversion of food objects
 * The object is gathered from the API, and is presented like an array of
 * FoodCategories containing an array of Dishes that contain themselves an array of Ingredients,
 * of Prices and of Images
 */


/**
 * @brief FoodData is the first level of the json file.
 * @param data An array of FoodCategory
 */
data class FoodData(
        var data: Array<FoodCategory>
){}

/**
 * @brief FoodCategory is what tells us if the dish is a starter, a main course or a dessert
 * @param name_fr Each category has a name in french
 * @param name_en A name in english
 * @param items An array of dishes that are part of the category
 */
data class FoodCategory(
        var name_fr: String? = null,
        var name_en: String? = null,
        var items: Array<Food>
){}

/**
 * @brief Food is what represents a Dish directly
 * @param id Is the id of the dish
 * @param name_fr The name of the dish in french
 * @param name_en The name of the food in english
 * @param id_category The id of the category in which it belongs (although categories don't have one)
 * @param categ_name_en The name in english of the parent category
 * @param categ_name_fr The name in french of the parent category
 * @param images An array of Strings that contains urls for the food images
 * @param ingredients An array of FoodIngredients items
 * @param prices An array of prices (we'll use the first one as a fixed price though)
 */
data class Food (
        var id: Int? = null,
        var name_fr: String? = null,
        var name_en: String? = null,
        var id_category: Int? = null,
        var categ_name_fr: String? = null,
        var categ_name_en: String? = null,
        var images: Array<String?>,
        var ingredients: Array<FoodIngredient>,
        var prices: Array<FoodPrice>
){}

/**
 * @brief FoodIngredient is a component of the Food data class
 * Each Food has an array of ingredients
 * @param id The id of the ingredient
 * @param id_shop The id of the shop from which the ingredient belongs to
 * @param name_fr The name in french of the ingredient
 * @param name_en The name in english of the ingredient
 * @param create_date The date at which has been added the ingredient
 * @param update_date The last update date of this ingredient
 * @param id_pizza This is a Legacy param. We don't use it in this project.
 */
data class FoodIngredient (
        var id: Int? = null,
        var id_shop: Int? = null,
        var name_fr: String? = null,
        var name_en: String? = null,
        var create_date: String? = null,
        var update_date: String? = null,
        var id_pizza: Int? = null
){
        override fun toString(): String {
                return name_fr as String
        }
}

/**
 * @brief FoodPrice is a structure for the item price even though each food has a single price
 * In our case most of the fields of this data class aren't being used.
 * @param id The id of the price
 * @param id_pizza Legacy component used to know which pizza it belongs to
 * @param id_size Legacy component used to know for which pizza size id it applies
 * @param price The value of the price
 * @param create_date The creation of the price in db
 * @param update_date The last update of the price in db
 * @param size Legacy component to know The size of the pizza
 */
data class FoodPrice(
        var id: Int? = null,
        var id_pizza: Int? = null,
        var id_size: Int? = null,
        var price: Float? = null,
        var create_date: String? = null,
        var update_date: String? = null,
        var size: String? = null
){}

/**
 * @brief a Serialized object is needed for Json and intent extras to work properly.
 * We create a SerializedFood Item to simplify the complex data structure for future uses as
 * most of the fields are not needed, SerializedFood only contains what is needed for a Food Item.
 * @param id The food id
 * @param name The food name in french
 * @param images An array of images urls
 * @param ingredients An array of ingredients french names
 * @param price A single price (as a String for display mainly)
 */
data class SerializedFood(
        var id: Int? = null,
        var name: String? = null,
        var images: ArrayList<String?>? = null,
        var ingredients: ArrayList<String?>? = null,
        var price: String? = null
): Serializable{}