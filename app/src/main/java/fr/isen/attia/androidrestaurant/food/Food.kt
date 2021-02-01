package fr.isen.attia.androidrestaurant.food

import java.io.Serializable

//Data classes needed for the JSON conversion
data class FoodData(
        var data: Array<FoodCategory>
){}

data class FoodCategory(
        var name_fr: String? = null,
        var name_en: String? = null,
        var items: Array<Food>
){}

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

data class FoodPrice(
        var id: Int? = null,
        var id_pizza: Int? = null,
        var id_size: Int? = null,
        var price: Float? = null,
        var create_date: String? = null,
        var update_date: String? = null,
        var size: String? = null
){}

data class SerializedFood(
        var id: Int? = null,
        var name: String? = null,
        var images: ArrayList<String?>? = null,
        var ingredients: ArrayList<String?>? = null,
        var price: String? = null
): Serializable{}