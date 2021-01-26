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

        fun createFoodList(numItems: Int) : ArrayList<FoodModel> {
            val foods = ArrayList<FoodModel>()
            for (i in 1..numItems){
                foods.add(FoodModel(++lastItemId, "Food $lastItemId"))
            }
            return foods
        }

        fun gatherFoodFromApi(context: Context) : ArrayList<FoodModel>{
            val foods = ArrayList<FoodModel>()
            val queue = Volley.newRequestQueue(context)

            val url = "http://test.api.catering.bluecodegames.com/menu"
            val params = HashMap<String,String>()
            params["id_shop"] = "1"
            val jsonObject = JSONObject(params as Map<*, *>)

            var foodData: FoodData? = null

            val request = JsonObjectRequest(Request.Method.POST, url,jsonObject,
                Response.Listener{
                    response -> Log.d("FOOD", Gson().fromJson<FoodData>(response.toString(), FoodData::class.java).toString())
                },
                Response.ErrorListener { Log.i("FOOD", "API Request error") })
            queue.add(request)
            Log.d("FOOD", foodData.toString())

            return foods
        }
    }
}