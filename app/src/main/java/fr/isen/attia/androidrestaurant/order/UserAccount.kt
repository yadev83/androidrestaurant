package fr.isen.attia.androidrestaurant.order

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.attia.androidrestaurant.basket.Basket
import fr.isen.attia.androidrestaurant.basket.Basket.Companion.USER_PREFERENCES_NAME
import fr.isen.attia.androidrestaurant.food.FoodModel
import org.json.JSONObject
import java.io.Serializable

class UserAccount() {
    var email: String = ""
    var password: String = ""
    var firstName: String = ""
    var lastName: String = ""

    var id: Int = -1

    fun validate(): Boolean{
        var valid = true

        if(email.length <= 12)
            valid = false
        if(password.length <= 6)
            valid = false

        return valid
    }


    fun registerRequest(context: Context){
        val queue = Volley.newRequestQueue(context)
        val jsonData = JSONObject()
        jsonData.put(ID_SHOP, idShop)
        jsonData.put(EMAIL, email)
        jsonData.put(PASSWORD, password)
        jsonData.put(FIRSTNAME, firstName)
        jsonData.put(LASTNAME, lastName)

        var request = JsonObjectRequest(
            Request.Method.POST,
            urlRegister,
            jsonData,
            { response ->
                parseResult(context, response.toString())
            },
            {
                Log.d("USER_ACCOUNT", "Error while doing the request to login/register")
            }
        )
        queue.add(request)
    }

    fun loginRequest(context: Context){
        if(email.isNullOrBlank()){
            load(context)?.let { parseResult(context, it) }
        }else{
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
                        parseResult(context, response.toString())
                        Log.d("USER_ACCOUNT", "Logged as " + get()?.email.toString())
                    },
                    {
                        Log.d("USER_ACCOUNT", "Error while doing the request to login/register")
                    }
            )
            queue.add(request)
        }
    }

    fun save(context: Context, request: String){
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(USER_ID, request)
        editor.apply()
    }

    /**
     * @brief Returns the last response string that has been put in the cache.
     * Returns null if cache is empty.
     */
    private fun load(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USER_ID, null)
    }

    private fun parseResult(context: Context, response: String){
        currentUser.value = GsonBuilder().create().fromJson(response, RequestResult::class.java)
        save(context, response)
    }

    fun logout(context: Context){
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(USER_ID)
        editor.apply()
        currentUser.value = null
    }

    inner class SerializedUserAccount(val id: Int, val email: String): Serializable{}

    inner class RequestResult(val data: SerializedUserAccount?){}

    companion object{
        const val ID_SHOP = "id_shop"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val FIRSTNAME = "firstname"
        const val LASTNAME = "lastname"
        const val USER_ID = "USER"

        const val urlRegister = "http://test.api.catering.bluecodegames.com/user/register"
        const val urlLogin = "http://test.api.catering.bluecodegames.com/user/login"
        const val idShop = 1

        var currentUser: MutableLiveData<RequestResult> = MutableLiveData()

        fun get(): SerializedUserAccount? {
            return currentUser.value?.data
        }
    }
}