package fr.isen.attia.androidrestaurant

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class FoodModel(val id: Int, val name: String) {
    companion object{
        private var lastItemId = 0
        var foods: ArrayList<FoodModel> = ArrayList<FoodModel>()

        fun createFoodList(numItems: Int){
            foods = ArrayList<FoodModel>()
            for (i in 1..numItems){
                foods.add(FoodModel(++lastItemId, "Food $lastItemId"))
            }
        }

        fun gatherFoodFromApi(context: Context){
            val queue = Volley.newRequestQueue(context)

            val url = "http://test.api.catering.bluecodegames.com/menu"
            val params = HashMap<String,String>()
            params["id_shop"] = "1"

            val paramsJsonObject = JSONObject(params as Map<*, *>)
            val request = JsonObjectRequest(Request.Method.POST, url, paramsJsonObject,
                Response.Listener{
                    response ->
                    run {
                        var foodData: FoodData = Gson().fromJson<FoodData>(response.toString(), FoodData::class.java)
                        //DEBUG LOG
                        Log.d("FOOD", foodData.toString())
                        foods = ArrayList<FoodModel>()
                        foodData.data.forEach{
                            it.items.forEach {
                                it.id?.let { it1 -> it.name_fr?.let { it2 -> FoodModel(it1, it2) } }?.let { it2 ->
                                    foods.add(
                                        it2
                                    )
                                    Log.d("FOOD", it2.name)
                                }
                            }
                        }

                    }
                },
                Response.ErrorListener { Log.i("FOOD", "API Request error") })

            queue.add(request)
        }
    }
}