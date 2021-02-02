package fr.isen.attia.androidrestaurant.food

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.attia.androidrestaurant.CategoryActivity
import org.json.JSONObject

/**
 * @brief FoodModel is the main class that represents a Food Item in memory.
 * It is made to gather a Food list from the API and contains the methods needed to manage them.
 * @param id The id of the food item
 * @param name The name of the food item
 * @param images An array of Strings containing images urls
 * @param ingredients An array of Food ingredients
 * @param price The price of the item
 */
class FoodModel(val id: Int, val name: String, var images: Array<String?>?, val ingredients: Array<FoodIngredient>?, val price: Float?){

    /**
     * @brief this method serializes a FoodModel and returns the corresponsing SerializedFood
     */
    fun serialize(): SerializedFood {
        var sId = id
        var sName = name
        var sImages: ArrayList<String?> = ArrayList()
        images?.forEach { image ->
            sImages.add(image)
        }
        var sIngredients: ArrayList<String?> = ArrayList()
        ingredients?.forEach { ingredient ->
            sIngredients.add(ingredient.name_fr)
        }
        var sPrice = price.toString()

        return SerializedFood(sId, sName, sImages, sIngredients, sPrice)
    }

    /**
     * @brief This companion object contains methods and variables for requests mainly.
     */
    companion object{
        /**
         * @brief This is an array of FoodModel containing the last requests result
         * It is defined as a "MutableLiveData" because we want to set a listener on it.
         * This way we are informed when it changes (that is, when a request is a success)
         */
        var currentFoods: MutableLiveData<ArrayList<FoodModel>> = MutableLiveData()

        private const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
        private const val REQUEST_CACHE = "REQUEST_CACHE"

        /**
         * @brief Returns the food model corresponding to the given dishId in params
         * @param dishId The id of the foodModel we want
         * @return FoodModel? if the id is present in currentFoods
         * @return null if there is no result
         */
        fun getFoodById(dishId: Int) : FoodModel?{
            currentFoods.value?.forEach{ food ->
                if(dishId == food.id){
                    return food
                }
            }
            return null
        }

        /**
         * @brief This method contacts the Api to fill the currentFoods array with realtime data
         * @param context The CategoryActivity in which we want to load food Models
         * @param title The title of the category used to refine the API request
         */
        fun gatherFoodFromApi(context: CategoryActivity, title: String){
            context.enableLoadingAnimation()
            resultFromCache(context)?.let {
                // La requete est en cache
                parseResult(it, title)
                context.disableLoadingAnimation()
            } ?: run {
                val queue = Volley.newRequestQueue(context)

                val url = "http://test.api.catering.bluecodegames.com/menu"
                val params = HashMap<String,String>()
                params["id_shop"] = "1"

                val paramsJsonObject = JSONObject(params as Map<*, *>)
                val request = JsonObjectRequest(Request.Method.POST, url, paramsJsonObject,
                    Response.Listener{
                            response ->
                        run {
                            cacheResult(context, response.toString())
                            parseResult(response.toString(), title)
                            context.disableLoadingAnimation()
                        }
                    },
                    Response.ErrorListener { Log.i("FOOD", "API Request error") })
                queue.add(request)
            }
        }

        /**
         * @brief This method is called by gatherFoodFromApi to parse a response string.
         * @param response The response string to parse
         * @param title The title of the category used to filter results
         */
        private fun parseResult(response: String, title: String){
            var foodData: FoodData = Gson().fromJson<FoodData>(response, FoodData::class.java)
            var foods = ArrayList<FoodModel>()
            foodData.data.forEach{category ->
                category.items.forEach {food ->
                    food.id?.let { foodId ->
                        food.name_fr?.let { foodName ->
                            FoodModel(foodId, foodName, food.images, food.ingredients, food.prices.first().price)
                        }
                    }?.let { foodModel ->
                        if(food.categ_name_fr == title){
                            foods.add(foodModel)
                            currentFoods.value = foods
                        }
                    }
                }
            }
        }

        /**
         * @brief This method is called by gatherFoodFromApi to cache the response string.
         * @param context Needed to access the application sharedPreferences (for storage)
         * @param response The string to put in cache
         */
        private fun cacheResult(context: Context, response: String) {
            val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(REQUEST_CACHE, response)
            editor.apply()
        }

        /**
         * @brief This method removes the request cache from the SharedPreferences
         */
        fun clearCache(context: Context){
            val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove(REQUEST_CACHE)
            editor.apply()
        }

        /**
         * @brief Returns the last response string that has been put in the cache.
         * Returns null if cache is empty.
         */
        private fun resultFromCache(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(REQUEST_CACHE, null)
        }
    }
}