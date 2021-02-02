package fr.isen.attia.androidrestaurant.order

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.attia.androidrestaurant.basket.Basket
import org.json.JSONObject
import java.io.Serializable

class UserAccount(var email: String?, var password: String?, var firstname: String?, var lastname: String?): Serializable {
    private var id: Int = -1

    fun validate(): Boolean{
        //TODO data validation
        return true
    }

    fun registerRequest(context: Context): Int{
        val queue = Volley.newRequestQueue(context)
        val jsonData = JSONObject()
        jsonData.put(ID_SHOP, idShop)
        jsonData.put(EMAIL, email)
        jsonData.put(PASSWORD, password)
        jsonData.put(FIRSTNAME, firstname)
        jsonData.put(LASTNAME, lastname)

        Log.d("JSON", jsonData.toString())

        var request = JsonObjectRequest(
            Request.Method.POST,
            urlRegister,
            jsonData,
            { response ->
                Log.d("REQUEST", response.toString())
            },
            { error ->
              error.message?.let{
                  Log.d("REQUEST", it)
              }  ?: run {
                  Log.d("REQUEST", error.toString())
                  Log.d("REQUEST", String(error.networkResponse.data))
              }
            }
        )
        queue.add(request)

        return 0
    }

    fun loginRequest(context: Context, email: String?, password: String?): Int{
        val queue = Volley.newRequestQueue(context)
        val jsonData = JSONObject()
        jsonData.put(ID_SHOP, idShop)
        jsonData.put(EMAIL, email)
        jsonData.put(PASSWORD, password)

        Log.d("JSON", jsonData.toString())

        var request = JsonObjectRequest(
            Request.Method.POST,
            urlLogin,
            jsonData,
            { response ->
                Log.d("REQUEST", response.toString())
                save(context)
            },
            { error ->
                error.message?.let {
                    Log.d("request", it)
                } ?: run {
                    Log.d("request", error.toString())
                    Log.d("request", String(error.networkResponse.data))
                }
            }
        )
        queue.add(request)

        return 0
    }

    fun save(context: Context){
        val sharedPreferences = context.getSharedPreferences(Basket.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(USER_ID, id)
        editor.apply()
    }

    companion object{
        const val ID_SHOP = "id_shop"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val FIRSTNAME = "firstname"
        const val LASTNAME = "lastname"
        const val USER_ID = "USER_ID"

        const val urlRegister = "http://test.api.catering.bluecodegames.com/user/register"
        const val urlLogin = "http://test.api.catering.bluecodegames.com/user/login"
        const val idShop = 1
    }
}