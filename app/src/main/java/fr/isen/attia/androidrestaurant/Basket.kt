package fr.isen.attia.androidrestaurant

import android.content.Context
import com.google.gson.GsonBuilder
import java.io.File
import java.io.Serializable

class Basket(val items: MutableList<BasketItem>): Serializable {
    var itemsCount: Int = 0
        get() {
            return if(items.count() > 0){
                items.map{ it.count }.reduce{acc, i -> acc + i}
            }else{
                0
            }
        }

    fun addItem(item: BasketItem){
        val existingItem = items.firstOrNull(){
            it.food.id == item.food.id
        }
        existingItem?.let{
            existingItem.count += item.count
        } ?: run {
            items.add(item)
        }
    }

    fun save(context: Context){
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, itemsCount)
        editor.apply()

        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FNAME)
        jsonFile.writeText(GsonBuilder().create().toJson(this))
    }

    operator fun get(position: Int): BasketItem {
        return items[position]
    }

    companion object{
        const val BASKET_FNAME = "basket.json"
        const val ITEMS_COUNT = "Items count"
        const val USER_PREFERENCES_NAME = "Users Preferences Name"

        fun getBasket(context: Context): Basket{
            val jsonFile = File(context.cacheDir.absolutePath + BASKET_FNAME)
            return if(jsonFile.exists()){
                val json = jsonFile.readText()
                GsonBuilder().create().fromJson(json, Basket::class.java)
            }else{
                Basket(mutableListOf())
            }
        }
    }
}

interface BasketItemInterface{
    fun onDeleteItem(item: BasketItem)
    fun onShowDetail(item: BasketItem)
}

class BasketItem(val food: SerializedFood, var count: Int): Serializable{}