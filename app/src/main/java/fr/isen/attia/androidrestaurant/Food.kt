package fr.isen.attia.androidrestaurant

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
        var ingredients: Array<Ingredients>
){}

data class Ingredients (
        var id: Int? = null,
        var id_shop: Int? = null,
        var name_fr: String? = null,
        var name_en: String? = null,
        var create_date: String? = null,
        var update_date: String? = null,
        var id_pizza: Int? = null
){}

data class Price(
        var id: Int? = null,
        var id_pizza: Int? = null,
        var id_size: Int? = null,
        var price: Int? = null,
        var create_date: String? = null,
        var update_date: String? = null,
        var size: String? = null
){}