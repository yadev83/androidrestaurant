package fr.isen.attia.androidrestaurant

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class FoodModel(val id: Int, val name: String, var images: Array<String?>?, val ingredients: Array<FoodIngredient>?, val price: Float?){
    var ready: Boolean = false

    companion object{
        private var lastItemId = 0
        var currentFoods: MutableLiveData<ArrayList<FoodModel>> = MutableLiveData()

        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
        const val REQUEST_CACHE = "REQUEST_CACHE"

        fun createFoodList(numItems: Int){
            var foods = ArrayList<FoodModel>()
            for (i in 1..numItems){
                foods.add(FoodModel(++lastItemId, "Food $lastItemId", null, null, null))
            }
            currentFoods.value = foods
        }

        fun getFoodById(dishId: Int) : FoodModel?{
            currentFoods.value?.forEach{ food ->
                if(dishId == food.id){
                    return food
                }
            }
            return null
        }

        fun gatherFoodFromApi(context: CategoryActivity, title: String){
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

        private fun parseResult(response: String, title: String){
            var foodData: FoodData = Gson().fromJson<FoodData>(response.toString(), FoodData::class.java)
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

        private fun cacheResult(context: Context, response: String) {
            val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(REQUEST_CACHE, response)
            editor.apply()
        }

        private fun resultFromCache(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(REQUEST_CACHE, null)
        }
    }
}