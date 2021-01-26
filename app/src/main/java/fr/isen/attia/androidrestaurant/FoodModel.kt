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

class FoodModel(val id: Int, val name: String){
    companion object{
        private var lastItemId = 0
        var currentFoods: MutableLiveData<ArrayList<FoodModel>> = MutableLiveData()

        fun createFoodList(numItems: Int){
            var foods = ArrayList<FoodModel>()
            for (i in 1..numItems){
                foods.add(FoodModel(++lastItemId, "Food $lastItemId"))
            }
            currentFoods.value = foods
        }

        fun gatherFoodFromApi(context: Context, title: String){
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
                        //Log.d("FOOD", foodData.toString())
                        var foods = ArrayList<FoodModel>()
                        foodData.data.forEach{
                            it.items.forEach {
                                it.id?.let { it1 -> it.name_fr?.let { it2 -> FoodModel(it1, it2) } }?.let { it2 ->
                                    if(it.categ_name_fr == title){
                                        foods.add(it2)
                                        currentFoods.value = foods
                                    }

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