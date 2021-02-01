package fr.isen.attia.androidrestaurant

import android.content.Context
import com.google.gson.GsonBuilder
import java.io.File
import java.io.Serializable

class Basket(val items: MutableList<BasketItem>): Serializable {

    var itemsCount: Int = 0
        get() {
            var acc = 0
            items.forEach(){item ->
                acc += item.count
            }
            return acc
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
        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FNAME)
        jsonFile.writeText(GsonBuilder().create().toJson(this))
    }

    companion object{
        const val BASKET_FNAME = "basket.json"

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

class BasketItem(val food: SerializedFood, var count: Int): Serializable{}